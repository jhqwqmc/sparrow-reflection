package net.momirealms.sparrow.reflection.field;

import org.jetbrains.annotations.Nullable;

public interface SDoubleField {

    double get(@Nullable Object instance);

    void set(@Nullable Object instance, double value);
}