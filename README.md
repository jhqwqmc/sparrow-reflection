## 引入此依赖

```kotlin
repositories {
    mavenCentral() // asm
    maven("https://repo.momirealms.net/releases/") // sparrow reflection
}
```
```kotlin
dependencies {
    // 必要的 asm 依赖，保持最新版本
    implementation("org.ow2.asm:asm:9.9")
    // 版本号可在 build.gradle.kts 查看
    implementation("net.momirealms:sparrow-reflection:{VERSION}")
}

tasks {
    // 请务必 relocate 此依赖
    shadowJar {
        relocate("net.momirealms.sparrow.reflection", "your.domain.libs.reflection")
    }
}
```

## 使用示例