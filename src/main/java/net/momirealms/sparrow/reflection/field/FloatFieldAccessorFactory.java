package net.momirealms.sparrow.reflection.field;

import net.momirealms.sparrow.reflection.SReflection;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@SuppressWarnings("DuplicatedCode")
final class FloatFieldAccessorFactory implements Opcodes {
    private FloatFieldAccessorFactory() {}

    static SFloatField create(Field field) throws Exception {
        if (field.getType() != float.class) {
            throw new IllegalArgumentException("Field must be of type float");
        }
        Class<?> owner = field.getDeclaringClass();
        String fieldName = field.getName();
        boolean isStatic = Modifier.isStatic(field.getModifiers());
        String internalClassName = Type.getInternalName(owner) + "$" + SReflection.PREFIX + "FloatAccessor_" + fieldName;
        byte[] bytes = generateByteCode(internalClassName, owner, fieldName, isStatic);
        MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(owner, SReflection.LOOKUP);
        MethodHandles.Lookup hiddenLookup = lookup.defineHiddenClass(bytes, true, MethodHandles.Lookup.ClassOption.NESTMATE);
        return (SFloatField) hiddenLookup.lookupClass().getDeclaredConstructor().newInstance();
    }

    private static byte[] generateByteCode(String className, Class<?> owner, String fieldName, boolean isStatic) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        String ownerInternalName = Type.getInternalName(owner);
        String interfaceInternalName = Type.getInternalName(SFloatField.class);

        cw.visit(V17, ACC_PUBLIC | ACC_FINAL, className, null, "java/lang/Object", new String[]{interfaceInternalName});

        // 默认构造函数
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        // 实现 float get(Object instance)
        mv = cw.visitMethod(ACC_PUBLIC, "get", "(Ljava/lang/Object;)F", null, null);
        mv.visitCode();
        if (isStatic) {
            mv.visitFieldInsn(GETSTATIC, ownerInternalName, fieldName, "F");
        } else {
            mv.visitVarInsn(ALOAD, 1);
            mv.visitTypeInsn(CHECKCAST, ownerInternalName);
            mv.visitFieldInsn(GETFIELD, ownerInternalName, fieldName, "F");
        }
        mv.visitInsn(FRETURN); // 使用 FRETURN 处理 float 返回值
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        // 实现 void set(Object instance, float value)
        mv = cw.visitMethod(ACC_PUBLIC, "set", "(Ljava/lang/Object;F)V", null, null);
        mv.visitCode();
        if (isStatic) {
            mv.visitVarInsn(FLOAD, 2); // 使用 FLOAD 读取 float 参数
            mv.visitFieldInsn(PUTSTATIC, ownerInternalName, fieldName, "F");
        } else {
            mv.visitVarInsn(ALOAD, 1);
            mv.visitTypeInsn(CHECKCAST, ownerInternalName);
            mv.visitVarInsn(FLOAD, 2); // 使用 FLOAD
            mv.visitFieldInsn(PUTFIELD, ownerInternalName, fieldName, "F");
        }
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        cw.visitEnd();
        return cw.toByteArray();
    }
}