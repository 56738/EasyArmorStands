plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    compileOnly("org.bukkit:bukkit:1.8.8-R0.1-SNAPSHOT") {
        isTransitive = false
    }
}
