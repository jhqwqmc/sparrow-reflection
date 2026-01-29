package net.momirealms.sparrow.reflection.type.matcher;

import java.lang.reflect.Type;

final class OrMatcher implements TypeMatcher {
    private final TypeMatcher matcher1;
    private final TypeMatcher matcher2;

    OrMatcher(TypeMatcher matcher1, TypeMatcher matcher2) {
        this.matcher1 = matcher1;
        this.matcher2 = matcher2;
    }

    @Override
    public boolean matches(Type type) {
        return this.matcher1.matches(type) || this.matcher2.matches(type);
    }
}
