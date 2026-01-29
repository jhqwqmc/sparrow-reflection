package net.momirealms.sparrow.reflection.type.matcher;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

final class GenericArrayMatcher implements TypeMatcher {
    private final TypeMatcher element;

    GenericArrayMatcher(TypeMatcher element) {
        this.element = element;
    }

    @Override
    public boolean matches(Type type) {
        if (!(type instanceof GenericArrayType genericArrayType)) return false;
        return this.element.matches(genericArrayType.getGenericComponentType());
    }
}
