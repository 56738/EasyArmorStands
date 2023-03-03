plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    implementation(project(":easyarmorstands-bukkit"))
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT") {
        isTransitive = false
    }
}
