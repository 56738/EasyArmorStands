plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    compileOnly(project(":easyarmorstands-module"))
    compileOnly(project(":easyarmorstands-api"))
    compileOnly("org.bukkit:bukkit:1.9.4-R0.1-SNAPSHOT") {
        isTransitive = false
    }
}
