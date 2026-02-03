package net.momirealms.sparrow.reflection.proxy;

import net.momirealms.sparrow.reflection.SReflection;
import net.momirealms.sparrow.reflection.clazz.SparrowClass;
import net.momirealms.sparrow.reflection.constructor.matcher.ConstructorMatcher;
import net.momirealms.sparrow.reflection.field.matcher.FieldMatcher;
import net.momirealms.sparrow.reflection.method.matcher.MethodMatcher;
import net.momirealms.sparrow.reflection.proxy.annotation.*;
import net.momirealms.sparrow.reflection.proxy.annotation.Type;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.*;
import java.util.*;

final class Util {
    private Util() {}

    public static List<Class<?>> getTopDownInterfaceHierarchy(Class<?> interfaceClass) {
        Set<Class<?>> hierarchy = new LinkedHashSet<>();
        hierarchy.add(interfaceClass);
        collectInterfaces(interfaceClass, hierarchy);
        // 父接口在前，子接口在后
        List<Class<?>> result = new ArrayList<>(hierarchy);
        Collections.reverse(result);
        return Collections.unmodifiableList(result);
    }

    private static void collectInterfaces(Class<?> clazz, Set<Class<?>> collector) {
        for (Class<?> parent : clazz.getInterfaces()) {
            collector.add(parent);
            collectInterfaces(parent, collector);
        }
    }

    public static Class<?> getProxiedClass(Class<?> clazz) {
        ReflectionProxy proxy = clazz.getDeclaredAnnotation(ReflectionProxy.class);
        if (proxy == null) {
            throw new IllegalArgumentException("Class " + clazz + " has no @ReflectionProxy annotation");
        }
        if (SReflection.getCustomCondition().test(proxy.condition())) {
            return Objects.requireNonNull(getProxiedClass(clazz, proxy), "Cannot find proxied class for " + clazz);
        } else {
            return null;
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Nullable
    private static Class<?> getProxiedClass(Class<?> clazz, ReflectionProxy proxy) {
        if (proxy.clazz() == Object.class && proxy.name().isEmpty() && proxy.names().length == 0) {
            throw new IllegalArgumentException("ReflectionProxy doesn't have value or class name set for class " + clazz);
        }
        if (proxy.clazz() != Object.class) {
            return proxy.clazz();
        }
        if (!proxy.name().isEmpty()) {
            if (proxy.ignoreRelocation()) {
                return SparrowClass.find(proxy.name().replace("{}", "."));
            } else {
                return SparrowClass.find(proxy.name());
            }
        }
        if (proxy.ignoreRelocation()) {
            return SparrowClass.find(Arrays.stream(proxy.names()).map(it -> it.replace("{}", ".")).toArray(String[]::new));
        } else {
            return SparrowClass.find(proxy.names());
        }
    }

    public static void checkArgumentCount(Method method, int expected) {
        int length = method.getParameterCount();
        if (length != expected) {
            throw new IllegalArgumentException("Method " + method.getName() + " has " + length + " parameters but expected " + expected);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public static Class<?> getParameterClass(Parameter parameter) {
        Type type = parameter.getDeclaredAnnotation(Type.class);
        if (type == null) {
            return parameter.getType();
        }
        if (type.clazz() == Object.class && type.name().isEmpty() && type.names().length == 0) {
            throw new IllegalArgumentException("Type annotation doesn't have value or class name set for parameter " + parameter);
        }
        if (type.clazz() != Object.class) {
            return getProxiedClass(type.clazz());
        }
        if (!type.name().isEmpty()) {
            if (type.ignoreRelocation()) {
                return SparrowClass.find(type.name().replace("{}", "."));
            } else {
                return SparrowClass.find(type.name());
            }
        }
        if (type.ignoreRelocation()) {
            return SparrowClass.find(Arrays.stream(type.names()).map(it -> it.replace("{}", ".")).toArray(String[]::new));
        } else {
            return SparrowClass.find(type.names());
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public static FieldMatcher getFieldMatcher(FieldGetter fieldGetter) {
        FieldMatcher matcher;
        if (!fieldGetter.name().isEmpty()) {
            matcher = FieldMatcher.named(fieldGetter.name());
        } else if (fieldGetter.names().length != 0) {
            matcher = FieldMatcher.named(fieldGetter.names());
        } else {
            throw new IllegalArgumentException("FieldGetter doesn't have name or names set");
        }
        if (fieldGetter.isStatic()) {
            matcher.and(FieldMatcher.staticField());
        } else {
            matcher.and(FieldMatcher.instanceField());
        }
        return matcher;
    }

    @SuppressWarnings("DuplicatedCode")
    public static FieldMatcher getFieldMatcher(FieldSetter fieldSetter) {
        FieldMatcher matcher;
        if (!fieldSetter.name().isEmpty()) {
            matcher = FieldMatcher.named(fieldSetter.name());
        } else if (fieldSetter.names().length != 0) {
            matcher = FieldMatcher.named(fieldSetter.names());
        } else {
            throw new IllegalArgumentException("FieldSetter doesn't have name or names set");
        }
        if (fieldSetter.isStatic()) {
            matcher.and(FieldMatcher.staticField());
        } else {
            matcher.and(FieldMatcher.instanceField());
        }
        return matcher;
    }

    public static MethodMatcher createMethodMatcher(MethodInvoker invoker) {
        MethodMatcher matcher;
        if (!invoker.name().isEmpty()) {
            matcher = MethodMatcher.named(invoker.name());
        } else if (invoker.names().length != 0) {
            matcher = MethodMatcher.named(invoker.names());
        } else {
            throw new IllegalArgumentException("MethodInvoker doesn't have name or names set");
        }
        if (invoker.isStatic()) {
            matcher.and(MethodMatcher.staticMethod());
        } else {
            matcher.and(MethodMatcher.instanceMethod());
        }
        return matcher;
    }

    public static void analyseAndApply(ProxyBuilder builder, List<Class<?>> interfaces) {
        for (Class<?> proxyClass : interfaces) {
            Class<?> targetClazz = Util.getProxiedClass(proxyClass);
            // 版本原因找不到类
            if (targetClazz == null) {
                continue;
            }
            analyseAndApply(proxyClass, targetClazz, builder);
        }
    }

    private static void analyseAndApply(Class<?> proxyClass, Class<?> targetClass, ProxyBuilder builder) {
        SparrowClass<?> spaClass = new SparrowClass<>(targetClass);
        for (Method method : proxyClass.getDeclaredMethods()) {

            // 字段获取
            FieldGetter fieldGetter = method.getAnnotation(FieldGetter.class);
            if (fieldGetter != null && SReflection.getCustomCondition().test(fieldGetter.condition())) {
                // 字段只需 名称 即可确定
                Field field = spaClass.getDeclaredField(Util.getFieldMatcher(fieldGetter));
                Objects.requireNonNull(field, "Field not found for proxy " + proxyClass + "#" + method.getName());
                Util.checkArgumentCount(method, Modifier.isStatic(field.getModifiers()) ? 0 : 1);
                builder.writeFieldGetter(method, field);
                continue;
            }

            // 字段设置
            FieldSetter fieldSetter = method.getAnnotation(FieldSetter.class);
            if (fieldSetter != null && SReflection.getCustomCondition().test(fieldSetter.condition())) {
                Field field = spaClass.getDeclaredField(Util.getFieldMatcher(fieldSetter));
                Objects.requireNonNull(field, "Field not found for proxy " + proxyClass + "#" + method.getName());
                Util.checkArgumentCount(method, Modifier.isStatic(field.getModifiers()) ? 1 : 2);
                builder.writeFieldSetter(method, field);
                continue;
            }

            // 方法调用
            MethodInvoker methodInvoker = method.getAnnotation(MethodInvoker.class);
            if (methodInvoker != null && SReflection.getCustomCondition().test(methodInvoker.condition())) {
                Class<?>[] parameterTypes = Arrays.stream(method.getParameters())
                        .skip(methodInvoker.isStatic() ? 0 : 1)
                        .map(Util::getParameterClass)
                        .toArray(Class<?>[]::new);
                // 方法只需要 参数 + 名称，即可确定唯一方法
                Method targetMethod = spaClass.getDeclaredMethod(Util.createMethodMatcher(methodInvoker).and(MethodMatcher.takeArguments(parameterTypes)));
                Objects.requireNonNull(targetMethod, "Method not found for proxy " + proxyClass + "#" + method.getName());
                if (!Modifier.isStatic(targetMethod.getModifiers())) {
                    if (method.getParameterCount() < 1) {
                        throw new IllegalArgumentException("Non-static method must have at least one argument");
                    }
                }
                builder.writeMethod(method, targetMethod);
                continue;
            }

            // 构造器
            ConstructorInvoker constructorInvoker = method.getAnnotation(ConstructorInvoker.class);
            if (constructorInvoker != null && SReflection.getCustomCondition().test(constructorInvoker.condition())) {
                // 构造器只需要 参数 即可确定
                Class<?>[] parameterTypes = Arrays.stream(method.getParameters())
                        .map(Util::getParameterClass)
                        .toArray(Class<?>[]::new);
                Constructor<?> constructor = spaClass.getDeclaredConstructor(ConstructorMatcher.takeArguments(parameterTypes));
                Objects.requireNonNull(constructor, "Constructor not found for proxy " + proxyClass + "#" + method.getName());
                builder.writeConstructor(method, constructor);
                continue;
            }
        }
    }
}