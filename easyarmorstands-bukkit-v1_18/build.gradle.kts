plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    implementation(project(":easyarmorstands-bukkit"))
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT") {
        isTransitive = false
        attributes {
            attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 17)
        }
    }
}

tasks {
    withType<JavaCompile> {
        options.release.set(8)
    }
}
