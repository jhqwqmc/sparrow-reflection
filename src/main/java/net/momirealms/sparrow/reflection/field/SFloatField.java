package net.momirealms.sparrow.reflection.field;

import org.jetbrains.annotations.Nullable;

public interface SFloatField {

    float get(@Nullable Object instance);

    void set(@Nullable Object instance, float value);
}