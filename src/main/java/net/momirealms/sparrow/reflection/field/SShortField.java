package net.momirealms.sparrow.reflection.field;

import org.jetbrains.annotations.Nullable;

public interface SShortField {

    short get(@Nullable Object instance);

    void set(@Nullable Object instance, short value);
}