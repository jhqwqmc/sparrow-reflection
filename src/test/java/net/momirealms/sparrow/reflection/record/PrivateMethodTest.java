package net.momirealms.sparrow.reflection.record;

import net.momirealms.sparrow.reflection.method.SMethod;
import net.momirealms.sparrow.reflection.clazz.SparrowClass;
import net.momirealms.sparrow.reflection.method.SparrowMethod;
import net.momirealms.sparrow.reflection.method.SMethod0;
import net.momirealms.sparrow.reflection.method.SMethod2;
import net.momirealms.sparrow.reflection.method.matcher.MethodMatcher;

import java.lang.reflect.Method;

public class PrivateMethodTest {

    public static void main(String[] args) throws Exception {
        PrivateMethodTestClass privateMethodTest = new PrivateMethodTestClass();
        SparrowClass<PrivateMethodTestClass> spaClass = SparrowClass.of(PrivateMethodTestClass.class);
        SparrowMethod sayHello = spaClass.getDeclaredSparrowMethod(MethodMatcher.named("sayHello"));
        SMethod asm0 = sayHello.asm();
        SMethod2 asm2 = sayHello.asm$2();

        Method method2 = sayHello.method;
        for (int i = 0; i < 100; i++) {
            method2.invoke(privateMethodTest, "MoMi", new int[] {1, 2, 3} );
        }

        long nano4 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            privateMethodTest.sayHello("MoMi", 1, 2, 3);
        }
        System.out.println(System.nanoTime() - nano4);

        long nano3 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            asm2.invoke(privateMethodTest, "MoMi", new int[]{1, 2, 3});
        }
        System.out.println(System.nanoTime() - nano3);

        long nano1 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            asm0.invoke(privateMethodTest, "MoMi", new int[]{1, 2, 3});
        }
        System.out.println(System.nanoTime() - nano1);


        System.out.println("================");
        SparrowMethod sayGoodbye = spaClass.getDeclaredSparrowMethod(MethodMatcher.named("sayGoodbye"));
        SMethod0 sMethod0 = sayGoodbye.asm$0();

        long nano5 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            sMethod0.invoke(null);
        }
        System.out.println(System.nanoTime() - nano5);

        long nano6 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            PrivateMethodTestClass.sayGoodbye();
        }
        System.out.println(System.nanoTime() - nano6);
    }
}
