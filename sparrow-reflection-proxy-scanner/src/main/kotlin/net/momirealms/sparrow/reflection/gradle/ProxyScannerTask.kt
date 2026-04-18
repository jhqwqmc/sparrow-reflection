package net.momirealms.sparrow.reflection.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.*
import org.objectweb.asm.*

abstract class ProxyScannerTask : DefaultTask() {

    @get:InputFiles
    abstract val classesDirs: ConfigurableFileCollection

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    @TaskAction
    fun scan() {
        val mapping = mutableMapOf<String, String>()
        val targetAnnotation = "Lnet/momirealms/sparrow/reflection/proxy/annotation/ReflectionProxy;"

        classesDirs.forEach { dir ->
            if (!dir.exists()) return@forEach

            dir.walkTopDown().filter { it.extension == "class" }.forEach { classFile ->
                classFile.inputStream().use { inputStream ->
                    val reader = ClassReader(inputStream)
                    val proxyClassName = reader.className.replace('/', '.')

                    reader.accept(object : ClassVisitor(Opcodes.ASM9) {
                        override fun visitAnnotation(desc: String, visible: Boolean): AnnotationVisitor? {
                            if (desc == targetAnnotation) {
                                return ProxyAnnotationVisitor { target ->
                                    mapping[target] = proxyClassName
                                }
                            }
                            return null
                        }
                    }, ClassReader.SKIP_CODE or ClassReader.SKIP_DEBUG)
                }
            }
        }

        val out = outputFile.get().asFile
        out.parentFile.mkdirs()
        out.writeText(mapping.entries.sortedBy { it.key }.joinToString("\n") { "${it.key} ${it.value}" })
    }
}

private class ProxyAnnotationVisitor(val onFound: (String) -> Unit) : AnnotationVisitor(Opcodes.ASM9) {
    override fun visit(name: String, value: Any) {
        if (name == "clazz" && value is Type) {
            val targetName = value.className
            if (targetName != "java.lang.Object") onFound(targetName)
        }
    }

    override fun visitArray(name: String): AnnotationVisitor? {
        if (name == "name") {
            return object : AnnotationVisitor(Opcodes.ASM9) {
                override fun visit(n: String?, value: Any) {
                    onFound(value.toString())
                }
            }
        }
        return null
    }
}