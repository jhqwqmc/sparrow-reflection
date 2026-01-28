package net.momirealms.sparrow.reflection.method.matcher;

import java.lang.reflect.Method;

final class NamesMatcher implements MethodMatcher {
    private final String[] names;

    NamesMatcher(String... names) {
        this.names = names;
    }

    @Override
    public boolean matches(Method method) {
        String methodName = method.getName();
        for (String name : names) {
            if (methodName.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
