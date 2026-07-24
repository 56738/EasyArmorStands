plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
    alias(libs.plugins.fabric.loom)
}

dependencies {
    api(project(":easyarmorstands-platform-modded"))
    api(libs.fabric.api)
    api(libs.fabric.loader)
    api(libs.fabric.permissions.api)
    api(libs.adventure.platform.fabric)
    minecraft(libs.minecraft)
}
