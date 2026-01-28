package net.momirealms.sparrow.reflection.field.matcher;

import java.lang.reflect.Field;

final class NotMatcher implements FieldMatcher {
    private final FieldMatcher matcher;

    NotMatcher(FieldMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Field field) {
        return !this.matcher.matches(field);
    }
}
