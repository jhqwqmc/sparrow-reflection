package net.momirealms.sparrow.reflection.constructor.matcher;

import java.lang.reflect.Constructor;

final class NotMatcher implements ConstructorMatcher {
    private final ConstructorMatcher matcher;

    NotMatcher(ConstructorMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Constructor<?> constructor) {
        return !this.matcher.matches(constructor);
    }
}
