plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    implementation(project(":easyarmorstands-bukkit"))
    compileOnly("org.spigotmc:spigot-api:1.18-R0.1-SNAPSHOT") {
        isTransitive = false
    }
}
