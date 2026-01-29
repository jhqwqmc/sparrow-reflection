package net.momirealms.sparrow.reflection.type.matcher;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

final class ParameterizedMatcher implements TypeMatcher {
    private final TypeMatcher rawMatcher;
    private final TypeMatcher ownerMatcher;
    private final TypeMatcher[] matchers;

    ParameterizedMatcher(@Nullable TypeMatcher rawMatcher, @Nullable TypeMatcher ownerMatcher, @Nullable TypeMatcher... matchers) {
        this.rawMatcher = rawMatcher;
        this.ownerMatcher = ownerMatcher;
        this.matchers = matchers;
    }

    @Override
    public boolean matches(Type type) {
        if (!(type instanceof ParameterizedType parameterizedType)) return false;
        Type rawType = parameterizedType.getRawType();
        if (this.rawMatcher != null) {
            if (!this.rawMatcher.matches(rawType)) return false;
        }
        if (this.ownerMatcher != null) {
            Type ownerType = parameterizedType.getOwnerType();
            if (!this.ownerMatcher.matches(ownerType)) return false;
        }
        if (this.matchers != null && this.matchers.length > 0) {
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length != matchers.length) return false;
            for (int i = 0; i < actualTypeArguments.length; i++) {
                if (!this.matchers[i].matches(actualTypeArguments[i])) return false;
            }
        }
        return true;
    }
}
