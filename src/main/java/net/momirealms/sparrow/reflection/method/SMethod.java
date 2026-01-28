package net.momirealms.sparrow.reflection.method;

import org.jetbrains.annotations.Nullable;

public interface SMethod {

    Object invoke(@Nullable Object instance, Object... args);
}
