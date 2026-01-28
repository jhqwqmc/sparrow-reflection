package net.momirealms.sparrow.reflection.record;

import net.momirealms.sparrow.reflection.clazz.SparrowClass;
import net.momirealms.sparrow.reflection.field.SparrowField;
import net.momirealms.sparrow.reflection.field.SField;
import net.momirealms.sparrow.reflection.field.matcher.FieldMatcher;

public class RecordTest {

    public static void main(String[] args) throws Exception {
        RecordTestClass recordTest = new RecordTestClass(1);
        SparrowClass<RecordTestClass> spaClass = SparrowClass.of(RecordTestClass.class);
        SparrowField valueField = spaClass.getDeclaredSparrowField(FieldMatcher.named("value"));


        SField asmAccessor = valueField.asm();
        long nano1 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            asmAccessor.get(recordTest);
        }
        System.out.println(System.nanoTime() - nano1);

        java.lang.reflect.Field f = spaClass.getDeclaredField(FieldMatcher.named("value"));
        long nano2 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            f.getInt(recordTest);
        }
        System.out.println(System.nanoTime() - nano2);

        SField mhAccessor = valueField.mh();
        long nano0 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            mhAccessor.get(recordTest);
        }
        System.out.println(System.nanoTime() - nano0);
    }
}
