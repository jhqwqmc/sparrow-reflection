package net.momirealms.sparrow.reflection.field.matcher;

import java.lang.reflect.Field;

final class AnyMatcher implements FieldMatcher {
    public static final AnyMatcher INSTANCE = new AnyMatcher();

    @Override
    public boolean matches(Field field) {
        return true;
    }
}
