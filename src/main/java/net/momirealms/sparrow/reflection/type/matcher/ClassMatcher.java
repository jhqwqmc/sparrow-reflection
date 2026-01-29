package net.momirealms.sparrow.reflection.type.matcher;

import java.lang.reflect.Type;

final class ClassMatcher implements TypeMatcher {
    private final Class<?> clazz;

    ClassMatcher(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean matches(Type type) {
        if (!(type instanceof Class<?> c)) return false;
        return this.clazz == c;
    }
}
