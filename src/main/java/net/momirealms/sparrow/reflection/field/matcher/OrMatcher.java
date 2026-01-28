package net.momirealms.sparrow.reflection.field.matcher;

import java.lang.reflect.Field;

final class OrMatcher implements FieldMatcher {
    private final FieldMatcher matcher1;
    private final FieldMatcher matcher2;

    OrMatcher(FieldMatcher matcher1, FieldMatcher matcher2) {
        this.matcher1 = matcher1;
        this.matcher2 = matcher2;
    }

    @Override
    public boolean matches(Field field) {
        return this.matcher1.matches(field) || this.matcher2.matches(field);
    }
}
