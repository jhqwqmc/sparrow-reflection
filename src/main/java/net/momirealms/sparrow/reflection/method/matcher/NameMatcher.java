package net.momirealms.sparrow.reflection.method.matcher;

import net.momirealms.sparrow.reflection.SReflection;

import java.lang.reflect.Method;

final class NameMatcher implements MethodMatcher {
    private final String name;
    private final boolean remap;

    NameMatcher(String name, boolean remap) {
        this.name = name;
        this.remap = remap;
    }

    @Override
    public boolean matches(Method method) {
        if (!this.remap || SReflection.getRemapper().isNoOp()) {
            return method.getName().equals(this.name);
        } else {
            Class<?> declaringClass = method.getDeclaringClass();
            Class<?>[] parameterTypes = method.getParameterTypes();
            return method.getName().equals(SReflection.getRemapper().remapMethodName(declaringClass, this.name, parameterTypes));
        }
    }
}
