package net.momirealms.sparrow.reflection.field;

import org.jetbrains.annotations.Nullable;

public interface SIntField {

    int get(@Nullable Object instance);

    void set(@Nullable Object instance, int value);
}
