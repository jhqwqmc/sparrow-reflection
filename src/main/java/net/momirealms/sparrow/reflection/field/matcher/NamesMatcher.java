package net.momirealms.sparrow.reflection.field.matcher;

import net.momirealms.sparrow.reflection.SReflection;

import java.lang.reflect.Field;

final class NamesMatcher implements FieldMatcher {
    private final String[] names;
    private final boolean remap;

    NamesMatcher(String[] names, boolean remap) {
        this.names = names;
        this.remap = remap;
    }

    @Override
    public boolean matches(Field field) {
        String fieldName = field.getName();
        if (!this.remap || SReflection.getRemapper().isNoOp()) {
            for (String name : this.names) {
                if (fieldName.equals(name)) {
                    return true;
                }
            }
        } else {
            Class<?> clazz = field.getDeclaringClass();
            for (String name : this.names) {
                if (fieldName.equals(SReflection.getRemapper().remapFieldName(clazz, name))) {
                    return true;
                }
            }
        }
        return false;
    }
}
