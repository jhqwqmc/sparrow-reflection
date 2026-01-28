package net.momirealms.sparrow.reflection.field.matcher;

import java.lang.reflect.Field;

final class AnyOfMatcher implements FieldMatcher {
    private final FieldMatcher[] matchers;

    AnyOfMatcher(FieldMatcher[] matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(Field field) {
        for (FieldMatcher matcher : this.matchers) {
            if (matcher.matches(field)) {
                return true;
            }
        }
        return false;
    }
}
