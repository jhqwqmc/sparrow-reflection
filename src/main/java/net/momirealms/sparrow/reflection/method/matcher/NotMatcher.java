package net.momirealms.sparrow.reflection.method.matcher;

import java.lang.reflect.Method;

final class NotMatcher implements MethodMatcher {
    private final MethodMatcher matcher;

    NotMatcher(MethodMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Method method) {
        return !this.matcher.matches(method);
    }
}
