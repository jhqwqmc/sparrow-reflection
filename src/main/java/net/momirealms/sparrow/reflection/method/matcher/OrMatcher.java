package net.momirealms.sparrow.reflection.method.matcher;

import java.lang.reflect.Method;

final class OrMatcher implements MethodMatcher {
    private final MethodMatcher matcher1;
    private final MethodMatcher matcher2;

    OrMatcher(MethodMatcher matcher1, MethodMatcher matcher2) {
        this.matcher1 = matcher1;
        this.matcher2 = matcher2;
    }

    @Override
    public boolean matches(Method method) {
        return this.matcher1.matches(method) || this.matcher2.matches(method);
    }
}
