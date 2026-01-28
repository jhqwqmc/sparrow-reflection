package net.momirealms.sparrow.reflection.method.matcher;

import java.lang.reflect.Method;

final class AnyOfMatcher implements MethodMatcher {
    private final MethodMatcher[] matchers;

    AnyOfMatcher(MethodMatcher[] matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(Method method) {
        for (MethodMatcher matcher : this.matchers) {
            if (matcher.matches(method)) {
                return true;
            }
        }
        return false;
    }
}
