package net.momirealms.sparrow.reflection.constructor.matcher;

import java.lang.reflect.Constructor;

final class AnyOfMatcher implements ConstructorMatcher {
    private final ConstructorMatcher[] matchers;

    AnyOfMatcher(ConstructorMatcher[] matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(Constructor<?> constructor) {
        for (ConstructorMatcher matcher : this.matchers) {
            if (matcher.matches(constructor)) {
                return true;
            }
        }
        return false;
    }
}
