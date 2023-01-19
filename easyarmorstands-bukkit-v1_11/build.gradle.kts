plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    implementation(project(":easyarmorstands-bukkit"))
    compileOnly("org.bukkit:bukkit:1.11-R0.1-SNAPSHOT") {
        isTransitive = false
    }
}
