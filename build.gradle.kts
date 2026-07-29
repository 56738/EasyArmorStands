plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.moddev) apply false
    alias(libs.plugins.fabric.loom) apply false
    alias(libs.plugins.minotaur) apply false
}

dependencies {
    api(project(":easyarmorstands-api"))
    api(project(":easyarmorstands-dependencies", configuration = "shadow"))
    api(project(":easyarmorstands-platform"))
    api(libs.cloud.annotations)
    api(libs.cloud.minecraft.extras) {
        exclude("net.kyori")
    }
    api(libs.cloud.core)
    api(libs.gizmo.common)
    annotationProcessor(libs.cloud.annotations)
}

tasks {
    jar {
        manifest {
            attributes("FMLModType" to "LIBRARY")
            attributes("Automatic-Module-Name" to "me.m56738.easyarmorstands")
        }
    }
}
