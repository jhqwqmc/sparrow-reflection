package net.momirealms.sparrow.reflection.field.matcher;

import java.lang.reflect.Field;

final class NameMatcher implements FieldMatcher {
    private final String name;

    NameMatcher(String name) {
        this.name = name;
    }

    @Override
    public boolean matches(Field field) {
        return field.getName().equals(this.name);
    }
}
