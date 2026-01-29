package net.momirealms.sparrow.reflection.type.matcher;

import java.lang.reflect.Type;

final class AllOfMatcher implements TypeMatcher {
    private final TypeMatcher[] matchers;

    AllOfMatcher(TypeMatcher[] matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(Type type) {
        for (TypeMatcher matcher : this.matchers) {
            if (!matcher.matches(type)) {
                return false;
            }
        }
        return true;
    }
}
