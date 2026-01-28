package net.momirealms.sparrow.reflection.method.matcher;

import java.lang.reflect.Method;

final class NameMatcher implements MethodMatcher {
    private final String name;

    NameMatcher(String name) {
        this.name = name;
    }

    @Override
    public boolean matches(Method method) {
        return method.getName().equals(this.name);
    }
}
