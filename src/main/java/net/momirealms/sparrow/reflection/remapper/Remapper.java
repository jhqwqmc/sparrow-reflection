package net.momirealms.sparrow.reflection.remapper;

public interface Remapper {

    String remapClassName(String className);

    String remapFieldName(Class<?> clazz, String fieldName);

    String remapMethodName(Class<?> clazz, String methodName, Class<?>... parameterTypes);
}
