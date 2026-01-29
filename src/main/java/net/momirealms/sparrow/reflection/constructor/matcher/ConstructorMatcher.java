package net.momirealms.sparrow.reflection.constructor.matcher;

import java.lang.reflect.Constructor;

public interface ConstructorMatcher {

    boolean matches(final Constructor<?> constructor);

    default ConstructorMatcher or(final ConstructorMatcher matcher) {
        return new OrMatcher(this, matcher);
    }

    default ConstructorMatcher and(final ConstructorMatcher matcher) {
        return new AndMatcher(this, matcher);
    }
}
