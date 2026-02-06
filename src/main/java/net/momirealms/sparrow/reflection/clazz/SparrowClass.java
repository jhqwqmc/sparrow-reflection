package net.momirealms.sparrow.reflection.clazz;

import net.momirealms.sparrow.reflection.SReflection;
import net.momirealms.sparrow.reflection.constructor.SparrowConstructor;
import net.momirealms.sparrow.reflection.constructor.UnsafeConstructor;
import net.momirealms.sparrow.reflection.constructor.matcher.ConstructorMatcher;
import net.momirealms.sparrow.reflection.field.SparrowField;
import net.momirealms.sparrow.reflection.field.matcher.FieldMatcher;
import net.momirealms.sparrow.reflection.method.SparrowMethod;
import net.momirealms.sparrow.reflection.method.matcher.MethodMatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

@SuppressWarnings("DuplicatedCode")
public final class SparrowClass<T> {
    public final Class<T> clazz;

    public SparrowClass(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <T> SparrowClass<T> of(@NotNull Class<T> clazz) {
        Objects.requireNonNull(clazz, "class cannot be null");
        return new SparrowClass<>(clazz);
    }

    @Nullable
    public static <T> SparrowClass<T> ofNullable(@Nullable Class<T> clazz) {
        return clazz == null ? null : new SparrowClass<>(clazz);
    }

    public Class<T> clazz() {
        return this.clazz;
    }

    public boolean isInstance(@NotNull Object object) {
        return this.clazz.isInstance(object);
    }

    public static Class<?> find(String... classes) {
        if (classes.length == 1) {
            return find(classes[0]);
        }
        for (String className : classes) {
            Class<?> clazz = find(className);
            if (clazz != null) {
                return clazz;
            }
        }
        return null;
    }

    public static Class<?> find(String clazz) {
        try {
            return Class.forName(SReflection.getRemapper().remapClassName(clazz));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Class<?> findNoRemap(String... classes) {
        if (classes.length == 1) {
            return findNoRemap(classes[0]);
        }
        for (String className : classes) {
            Class<?> clazz = findNoRemap(className);
            if (clazz != null) {
                return clazz;
            }
        }
        return null;
    }

    public static Class<?> findNoRemap(String clazz) {
        try {
            return Class.forName(clazz);
        } catch (Throwable e) {
            return null;
        }
    }

    public static boolean exists(String clazz) {
        try {
            Class.forName(SReflection.getRemapper().remapClassName(clazz));
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean existsNoRemap(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /*

    fields

     */

    public Field getField(FieldMatcher matcher) {
        for (Field field : this.clazz.getFields()) {
            if (matcher.matches(field)) {
                return field;
            }
        }
        return null;
    }

    public Field getDeclaredField(FieldMatcher matcher) {
        for (Field field : this.clazz.getDeclaredFields()) {
            if (matcher.matches(field)) {
                return SReflection.setAccessible(field);
            }
        }
        return null;
    }

    public Field getField(FieldMatcher matcher, int index) {
        Field[] fields = this.clazz.getFields();
        int i = 0;
        for (Field field : fields) {
            if (matcher.matches(field)) {
                if (i == index) {
                    return field;
                }
                i++;
            }
        }
        return null;
    }

    public Field getDeclaredField(FieldMatcher matcher, int index) {
        Field[] fields = this.clazz.getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            if (matcher.matches(field)) {
                if (i == index) {
                    return SReflection.setAccessible(field);
                }
                i++;
            }
        }
        return null;
    }

    public Field getFieldBackwards(FieldMatcher matcher, int index) {
        Field[] fields = this.clazz.getFields();
        int i = 0;
        for (int j = fields.length - 1; j >= 0; j--) {
            if (matcher.matches(fields[j])) {
                if (i == index) {
                    return fields[j];
                }
                i++;
            }
        }
        return null;
    }

    public Field getDeclaredFieldBackwards(FieldMatcher matcher, int index) {
        Field[] fields = this.clazz.getDeclaredFields();
        int i = 0;
        for (int j = fields.length - 1; j >= 0; j--) {
            if (matcher.matches(fields[j])) {
                if (i == index) {
                    return SReflection.setAccessible(fields[j]);
                }
                i++;
            }
        }
        return null;
    }

    public SparrowField getSparrowField(FieldMatcher matcher) {
        return SparrowField.ofNullable(getField(matcher));
    }

    public SparrowField getDeclaredSparrowField(FieldMatcher matcher) {
        return SparrowField.ofNullable(getDeclaredField(matcher));
    }

    public SparrowField getSparrowField(FieldMatcher matcher, int index) {
        return SparrowField.ofNullable(getField(matcher, index));
    }

    public SparrowField getDeclaredSparrowField(FieldMatcher matcher, int index) {
        return SparrowField.ofNullable(getDeclaredField(matcher, index));
    }

    public SparrowField getSparrowFieldBackwards(FieldMatcher matcher, int index) {
        return SparrowField.ofNullable(getFieldBackwards(matcher, index));
    }

    public SparrowField getDeclaredSparrowFieldBackwards(FieldMatcher matcher, int index) {
        return SparrowField.ofNullable(getDeclaredFieldBackwards(matcher, index));
    }

    /*

    constructors

     */

    public UnsafeConstructor unsafeConstructor() {
        return new UnsafeConstructor(this.clazz);
    }

    @SuppressWarnings("unchecked")
    public Constructor<T> getConstructor(ConstructorMatcher matcher) {
        for (Constructor<?> constructor : this.clazz.getConstructors()) {
            if (matcher.matches(constructor)) {
                return (Constructor<T>) constructor;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Constructor<T> getDeclaredConstructor(ConstructorMatcher matcher) {
        for (Constructor<?> constructor : this.clazz.getDeclaredConstructors()) {
            if (matcher.matches(constructor)) {
                return (Constructor<T>) SReflection.setAccessible(constructor);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Constructor<T> getConstructor(ConstructorMatcher matcher, int index) {
        Constructor<?>[] constructors = this.clazz.getConstructors();
        int i = 0;
        for (Constructor<?> constructor : constructors) {
            if (matcher.matches(constructor)) {
                if (i == index) {
                    return (Constructor<T>) constructor;
                }
                i++;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Constructor<T> getDeclaredConstructor(ConstructorMatcher matcher, int index) {
        Constructor<?>[] constructors = this.clazz.getDeclaredConstructors();
        int i = 0;
        for (Constructor<?> constructor : constructors) {
            if (matcher.matches(constructor)) {
                if (i == index) {
                    return (Constructor<T>) SReflection.setAccessible(constructor);
                }
                i++;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Constructor<T> getConstructorBackwards(ConstructorMatcher matcher, int index) {
        Constructor<?>[] constructors = this.clazz.getConstructors();
        int i = 0;
        for (int j = constructors.length - 1; j >= 0; j--) {
            if (matcher.matches(constructors[j])) {
                if (i == index) {
                    return (Constructor<T>) constructors[j];
                }
                i++;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Constructor<T> getDeclaredConstructorBackwards(ConstructorMatcher matcher, int index) {
        Constructor<?>[] constructors = this.clazz.getDeclaredConstructors();
        int i = 0;
        for (int j = constructors.length - 1; j >= 0; j--) {
            if (matcher.matches(constructors[j])) {
                if (i == index) {
                    return (Constructor<T>) SReflection.setAccessible(constructors[j]);
                }
                i++;
            }
        }
        return null;
    }

    public SparrowConstructor<T> getSparrowConstructor(ConstructorMatcher matcher) {
        return SparrowConstructor.ofNullable(getConstructor(matcher));
    }

    public SparrowConstructor<T> getDeclaredSparrowConstructor(ConstructorMatcher matcher) {
        return SparrowConstructor.ofNullable(getDeclaredConstructor(matcher));
    }

    public SparrowConstructor<T> getSparrowConstructor(ConstructorMatcher matcher, int index) {
        return SparrowConstructor.ofNullable(getConstructor(matcher, index));
    }

    public SparrowConstructor<T> getDeclaredSparrowConstructor(ConstructorMatcher matcher, int index) {
        return SparrowConstructor.ofNullable(getDeclaredConstructor(matcher, index));
    }

    public SparrowConstructor<T> getSparrowConstructorBackwards(ConstructorMatcher matcher, int index) {
        return SparrowConstructor.ofNullable(getConstructorBackwards(matcher, index));
    }

    public SparrowConstructor<T> getDeclaredSparrowConstructorBackwards(ConstructorMatcher matcher, int index) {
        return SparrowConstructor.ofNullable(getDeclaredConstructorBackwards(matcher, index));
    }

    /*

    methods

     */

    public Method getMethod(MethodMatcher matcher) {
        Method[] methods = this.clazz.getMethods();
        for (Method method : methods) {
            if (matcher.matches(method)) {
                return method;
            }
        }
        return null;
    }

    public Method getDeclaredMethod(MethodMatcher matcher) {
        Method[] methods = this.clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (matcher.matches(method)) {
                return SReflection.setAccessible(method);
            }
        }
        return null;
    }

    public Method getMethod(MethodMatcher matcher, int index) {
        Method[] methods = this.clazz.getMethods();
        int i = 0;
        for (Method method : methods) {
            if (matcher.matches(method)) {
                if (i == index) {
                    return method;
                }
                i++;
            }
        }
        return null;
    }

    public Method getDeclaredMethod(MethodMatcher matcher, int index) {
        Method[] methods = this.clazz.getDeclaredMethods();
        int i = 0;
        for (Method method : methods) {
            if (matcher.matches(method)) {
                if (i == index) {
                    return SReflection.setAccessible(method);
                }
                i++;
            }
        }
        return null;
    }

    public Method getMethodBackwards(MethodMatcher matcher, int index) {
        Method[] methods = this.clazz.getMethods();
        int i = 0;
        for (int j = methods.length - 1; j >= 0; j--) {
            if (matcher.matches(methods[j])) {
                if (i == index) {
                    return methods[j];
                }
                i++;
            }
        }
        return null;
    }

    public Method getDeclaredMethodBackwards(MethodMatcher matcher, int index) {
        Method[] methods = this.clazz.getDeclaredMethods();
        int i = 0;
        for (int j = methods.length - 1; j >= 0; j--) {
            if (matcher.matches(methods[j])) {
                if (i == index) {
                    return SReflection.setAccessible(methods[j]);
                }
                i++;
            }
        }
        return null;
    }

    public SparrowMethod getDeclaredSparrowMethod(MethodMatcher matcher) {
        return SparrowMethod.ofNullable(getDeclaredMethod(matcher));
    }

    public SparrowMethod getSparrowMethod(MethodMatcher matcher) {
        return SparrowMethod.ofNullable(getMethod(matcher));
    }

    public SparrowMethod getDeclaredSparrowMethod(MethodMatcher matcher, int index) {
        return SparrowMethod.ofNullable(getDeclaredMethod(matcher, index));
    }

    public SparrowMethod getSparrowMethod(MethodMatcher matcher, int index) {
        return SparrowMethod.ofNullable(getMethod(matcher, index));
    }

    public SparrowMethod getSparrowMethodBackwards(MethodMatcher matcher, int index) {
        return SparrowMethod.ofNullable(getMethodBackwards(matcher, index));
    }

    public SparrowMethod getDeclaredSparrowMethodBackwards(MethodMatcher matcher, int index) {
        return SparrowMethod.ofNullable(getDeclaredMethodBackwards(matcher, index));
    }
}
