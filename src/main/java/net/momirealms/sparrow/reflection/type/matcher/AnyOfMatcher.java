package net.momirealms.sparrow.reflection.type.matcher;

import java.lang.reflect.Type;

final class AnyOfMatcher implements TypeMatcher {
    private final TypeMatcher[] matchers;

    AnyOfMatcher(TypeMatcher[] matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(Type type) {
        for (TypeMatcher matcher : this.matchers) {
            if (matcher.matches(type)) {
                return true;
            }
        }
        return false;
    }
}
