package net.momirealms.sparrow.reflection.field;

import org.jetbrains.annotations.Nullable;

public interface SBooleanField {

    boolean get(@Nullable Object instance);

    void set(@Nullable Object instance, boolean value);
}