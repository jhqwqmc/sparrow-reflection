package net.momirealms.sparrow.reflection.field.matcher;

import net.momirealms.sparrow.reflection.type.matcher.TypeMatcher;

import java.lang.reflect.Field;

final class GenericTypeMatcher implements FieldMatcher {
    private final TypeMatcher matcher;

    GenericTypeMatcher(TypeMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Field field) {
        return this.matcher.matches(field.getGenericType());
    }
}
