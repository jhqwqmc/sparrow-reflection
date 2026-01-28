package net.momirealms.sparrow.reflection.method.matcher;

import java.lang.reflect.Method;

final class AllOfMatcher implements MethodMatcher {
    private final MethodMatcher[] matchers;

    AllOfMatcher(MethodMatcher[] matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(Method method) {
        for (MethodMatcher matcher : this.matchers) {
            if (!matcher.matches(method)) {
                return false;
            }
        }
        return true;
    }
}
