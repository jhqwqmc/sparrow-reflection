package net.momirealms.sparrow.reflection.field.matcher;

import net.momirealms.sparrow.reflection.SReflection;

import java.lang.reflect.Field;

final class NameMatcher implements FieldMatcher {
    private final String name;
    private final boolean remap;

    NameMatcher(String name, boolean remap) {
        this.name = name;
        this.remap = remap;
    }

    @Override
    public boolean matches(Field field) {
        if (!this.remap || SReflection.getRemapper().isNoOp()) {
            return field.getName().equals(this.name);
        } else {
            return field.getName().equals(SReflection.getRemapper().remapFieldName(field.getDeclaringClass(), this.name));
        }
    }
}
