package net.momirealms.sparrow.reflection.field;

import org.jetbrains.annotations.Nullable;

public interface SLongField {

    long get(@Nullable Object instance);

    void set(@Nullable Object instance, long value);
}