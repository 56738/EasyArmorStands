plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
    alias(libs.plugins.fabric.loom)
}

dependencies {
    api(project(":easyarmorstands-modded-api"))
    api(project(":easyarmorstands-platform-fabric"))
    minecraft(libs.minecraft)
}
