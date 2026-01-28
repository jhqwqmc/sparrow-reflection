package net.momirealms.sparrow.reflection.field;

import org.jetbrains.annotations.Nullable;

public interface SByteField {

    byte get(@Nullable Object instance);

    void set(@Nullable Object instance, byte value);
}