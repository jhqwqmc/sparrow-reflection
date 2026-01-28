package net.momirealms.sparrow.reflection.method.matcher;

import java.lang.reflect.Method;

final class TakeArgumentsMatcher implements MethodMatcher {
    private final Class<?>[] arguments;

    TakeArgumentsMatcher(Class<?>... arguments) {
        this.arguments = arguments;
    }

    @Override
    public boolean matches(Method method) {
        Class<?>[] params = method.getParameterTypes();
        for (int i = 0; i < this.arguments.length; i++) {
            if (this.arguments[i] != params[i])
                return false;
        }
        return true;
    }
}
