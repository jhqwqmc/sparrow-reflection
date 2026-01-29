package net.momirealms.sparrow.reflection.field.matcher;

import java.lang.reflect.Field;

public interface FieldMatcher {

    boolean matches(final Field field);

    default FieldMatcher or(final FieldMatcher matcher) {
        return new OrMatcher(this, matcher);
    }

    default FieldMatcher and(final FieldMatcher matcher) {
        return new AndMatcher(this, matcher);
    }
}
