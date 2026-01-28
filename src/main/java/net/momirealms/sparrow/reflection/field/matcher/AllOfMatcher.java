package net.momirealms.sparrow.reflection.field.matcher;

import java.lang.reflect.Field;

final class AllOfMatcher implements FieldMatcher {
    private final FieldMatcher[] matchers;

    AllOfMatcher(FieldMatcher[] matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(Field field) {
        for (FieldMatcher matcher : this.matchers) {
            if (!matcher.matches(field)) {
                return false;
            }
        }
        return true;
    }
}
