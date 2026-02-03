package net.momirealms.sparrow.reflection.proxy;

import net.momirealms.sparrow.reflection.SReflection;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public final class ASMProxyFactory implements Opcodes {
    private ASMProxyFactory() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<T> proxy) {
        List<Class<?>> interfaces = Util.getTopDownInterfaceHierarchy(proxy);
        Class<?> targetClass = Util.getProxiedClass(proxy);
        if (targetClass == null) return null;
        String internalClassName = Type.getInternalName(targetClass) + "$Proxy";
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(V17, ACC_PUBLIC | ACC_FINAL, internalClassName, null, "java/lang/Object", new String[]{Type.getInternalName(proxy)});

        // 添加构造方法
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }

        // 写入接口方法
        AsmProxyBuilder builder = new AsmProxyBuilder(cw, internalClassName);
        Util.analyseAndApply(builder, interfaces);

        // 完成类
        cw.visitEnd();

        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(targetClass, SReflection.LOOKUP);
            MethodHandles.Lookup hiddenLookup = lookup.defineHiddenClass(cw.toByteArray(), true, MethodHandles.Lookup.ClassOption.NESTMATE);
            Class<?> proxyClass = hiddenLookup.lookupClass();
            for (FinalFieldHandle finalFieldHandle : builder.finalFields()) {
                Field handleField = proxyClass.getDeclaredField("HANDLE_" + finalFieldHandle.name().toUpperCase(Locale.ROOT));
                SReflection.setAccessible(handleField).set(null, finalFieldHandle.handle());
            }
            return (T) proxyClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create proxy class " + proxy, e);
        }
    }
}
