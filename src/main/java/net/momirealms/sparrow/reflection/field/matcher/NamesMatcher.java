package net.momirealms.sparrow.reflection.field.matcher;

import java.lang.reflect.Field;

final class NamesMatcher implements FieldMatcher {
    private final String[] names;

    NamesMatcher(String... names) {
        this.names = names;
    }

    @Override
    public boolean matches(Field field) {
        String methodName = field.getName();
        for (String name : names) {
            if (methodName.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
