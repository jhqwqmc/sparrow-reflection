package net.momirealms.sparrow.reflection.method.matcher;

import net.momirealms.sparrow.reflection.type.matcher.TypeMatcher;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

final class TakeGenericArgumentsMatcher implements MethodMatcher {
    private final TypeMatcher[] arguments;

    TakeGenericArgumentsMatcher(TypeMatcher... arguments) {
        this.arguments = arguments;
    }

    @Override
    public boolean matches(Method method) {
        Type[] params = method.getGenericParameterTypes();
        if (params.length != arguments.length) return false;
        for (int i = 0; i < this.arguments.length; i++) {
            if (!this.arguments[i].matches(params[i])) return false;
        }
        return true;
    }
}
