package net.momirealms.sparrow.reflection.type.matcher;

import java.lang.reflect.Type;

public interface TypeMatcher {

    boolean matches(final Type type);

    default TypeMatcher or(final TypeMatcher matcher) {
        return new OrMatcher(this, matcher);
    }

    default TypeMatcher and(final TypeMatcher matcher) {
        return new AndMatcher(this, matcher);
    }
}
