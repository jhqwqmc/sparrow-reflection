package net.momirealms.sparrow.reflection.proxy;

import java.lang.invoke.MethodHandle;

record FinalFieldHandle(String name, MethodHandle handle) {
}
