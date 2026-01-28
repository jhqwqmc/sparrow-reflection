package net.momirealms.sparrow.reflection.field;

import org.jetbrains.annotations.Nullable;

public interface SCharField {

    char get(@Nullable Object instance);

    void set(@Nullable Object instance, char value);
}