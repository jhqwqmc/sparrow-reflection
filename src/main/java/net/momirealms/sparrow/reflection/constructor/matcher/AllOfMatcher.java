package net.momirealms.sparrow.reflection.constructor.matcher;

import java.lang.reflect.Constructor;

final class AllOfMatcher implements ConstructorMatcher {
    private final ConstructorMatcher[] matchers;

    AllOfMatcher(ConstructorMatcher[] matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(Constructor<?> constructor) {
        for (ConstructorMatcher matcher : this.matchers) {
            if (!matcher.matches(constructor)) {
                return false;
            }
        }
        return true;
    }
}
