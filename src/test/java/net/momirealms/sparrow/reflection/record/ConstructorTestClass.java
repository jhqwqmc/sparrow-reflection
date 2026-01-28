package net.momirealms.sparrow.reflection.record;

import java.util.Arrays;

public class ConstructorTestClass {
    private final String a;
    private final int b;
    private final String[] c;

    private ConstructorTestClass(String a, int b, String... c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String toString() {
        return "ConstructorTestClass{" +
                "a='" + a + '\'' +
                ", b=" + b +
                ", c=" + Arrays.toString(c) +
                '}';
    }
}
