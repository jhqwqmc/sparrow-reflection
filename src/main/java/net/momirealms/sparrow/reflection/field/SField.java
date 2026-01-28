package net.momirealms.sparrow.reflection.field;

import org.jetbrains.annotations.Nullable;

public interface SField {

    default int getInt(Object instance) {
        return (int) get(instance);
    }

    default long getLong(Object instance) {
        return (long) get(instance);
    }

    default double getDouble(Object instance) {
        return (double) get(instance);
    }

    default boolean getBoolean(Object instance) {
        return (boolean) get(instance);
    }

    default float getFloat(Object instance) {
        return (float) get(instance);
    }

    default void setInt(Object instance, int value) {
        set(instance, value);
    }

    default void setLong(Object instance, long value) {
        set(instance, value);
    }

    default void setDouble(Object instance, double value) {
        set(instance, value);
    }

    default void setFloat(Object instance, float value) {
        set(instance, value);
    }

    default void setBoolean(Object instance, boolean value) {
        set(instance, value);
    }

    Object get(@Nullable Object instance);

    void set(@Nullable Object instance, @Nullable Object value);
}