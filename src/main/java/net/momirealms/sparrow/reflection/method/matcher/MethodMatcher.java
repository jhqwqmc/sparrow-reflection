package net.momirealms.sparrow.reflection.method.matcher;

import java.lang.reflect.Method;

public interface MethodMatcher {

    boolean matches(final Method method);

    default MethodMatcher or(final MethodMatcher matcher) {
        return new OrMatcher(this, matcher);
    }

    default MethodMatcher and(final MethodMatcher matcher) {
        return new AndMatcher(this, matcher);
    }
}
