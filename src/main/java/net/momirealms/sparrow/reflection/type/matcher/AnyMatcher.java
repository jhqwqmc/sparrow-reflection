package net.momirealms.sparrow.reflection.type.matcher;

import java.lang.reflect.Type;

final class AnyMatcher implements TypeMatcher {
    public static final AnyMatcher INSTANCE = new AnyMatcher();

    @Override
    public boolean matches(Type type) {
        return true;
    }
}
