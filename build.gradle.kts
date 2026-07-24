plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.moddev) apply false
    alias(libs.plugins.fabric.loom) apply false
}

dependencies {
    api(project(":easyarmorstands-api"))
    api(project(":easyarmorstands-assets"))
    api(project(":easyarmorstands-platform"))
    api(libs.cloud.annotations)
    api(libs.cloud.minecraft.extras) {
        exclude("net.kyori")
    }
    api(libs.cloud.core)
    api(libs.configurate.yaml)
    api(libs.gizmo.common)
    annotationProcessor(libs.cloud.annotations)
}
