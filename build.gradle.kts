
plugins {
    id("easyarmorstands.base")
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
