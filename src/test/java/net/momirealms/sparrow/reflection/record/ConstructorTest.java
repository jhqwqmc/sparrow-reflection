package net.momirealms.sparrow.reflection.record;

import net.momirealms.sparrow.reflection.clazz.SparrowClass;
import net.momirealms.sparrow.reflection.constructor.SConstructor;
import net.momirealms.sparrow.reflection.constructor.SConstructor3;
import net.momirealms.sparrow.reflection.constructor.SConstructor9;
import net.momirealms.sparrow.reflection.constructor.SparrowConstructor;

public class ConstructorTest {

    public static void main(String[] args) {
        SparrowClass<ConstructorTestClass> spaClass = SparrowClass.of(ConstructorTestClass.class);
        SparrowConstructor<ConstructorTestClass> constructor = (SparrowConstructor<ConstructorTestClass>) SparrowConstructor.of(spaClass.clazz.getDeclaredConstructors()[0]);
        SConstructor asm = constructor.asm();
        SConstructor3 asm2 = constructor.asm$3();

        long nano1 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            asm.newInstance("666", 11, new String[] { "a", "b", "c" });
        }
        System.out.println(System.nanoTime() - nano1);

        long nano2 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            asm2.newInstance("666", 11, new String[] { "a", "b", "c" });
        }
        System.out.println(System.nanoTime() - nano2);
    }
}
