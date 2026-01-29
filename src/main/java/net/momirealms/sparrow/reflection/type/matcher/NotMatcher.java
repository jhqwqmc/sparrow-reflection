package net.momirealms.sparrow.reflection.type.matcher;

import java.lang.reflect.Type;

final class NotMatcher implements TypeMatcher {
    private final TypeMatcher matcher;

    NotMatcher(TypeMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Type type) {
        return !this.matcher.matches(type);
    }
}
