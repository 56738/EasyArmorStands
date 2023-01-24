plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    implementation(project(":easyarmorstands-bukkit"))
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT") {
        isTransitive = false
    }
}
