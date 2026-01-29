package net.momirealms.sparrow.reflection.util;

import java.util.Map;
import java.util.function.Function;

public final class StringPool {
    private final Map<String, String> map;

    public StringPool(Map<String, String> map) {
        this.map = map;
    }

    public String get(final String key) {
        return this.map.computeIfAbsent(key, Function.identity());
    }
}
