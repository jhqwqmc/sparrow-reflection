package net.momirealms.sparrow.reflection.constructor.matcher;

import java.lang.reflect.Constructor;

final class AndMatcher implements ConstructorMatcher {
    private final ConstructorMatcher matcher1;
    private final ConstructorMatcher matcher2;

    AndMatcher(ConstructorMatcher matcher1, ConstructorMatcher matcher2) {
        this.matcher1 = matcher1;
        this.matcher2 = matcher2;
    }

    @Override
    public boolean matches(Constructor<?> constructor) {
        return this.matcher1.matches(constructor) && this.matcher2.matches(constructor);
    }
}
