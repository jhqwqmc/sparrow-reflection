package net.momirealms.sparrow.reflection.method.matcher;

import java.lang.reflect.Method;

final class AnyMatcher implements MethodMatcher {
    public static final MethodMatcher INSTANCE = new AnyMatcher();

    private AnyMatcher() {}

    @Override
    public boolean matches(Method method) {
        return true;
    }
}
