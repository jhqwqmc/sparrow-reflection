package net.momirealms.sparrow.reflection.constructor.matcher;

import java.lang.reflect.Constructor;

final class AnyMatcher implements ConstructorMatcher {
    public static final ConstructorMatcher INSTANCE = new AnyMatcher();

    private AnyMatcher() {}

    @Override
    public boolean matches(Constructor<?> constructor) {
        return true;
    }
}
