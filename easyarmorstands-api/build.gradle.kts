plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
}

dependencies {
    compileOnly(libs.bukkit)
    api(libs.adventure.api)
    api(libs.joml)
}
