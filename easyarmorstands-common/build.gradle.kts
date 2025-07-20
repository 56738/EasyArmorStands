plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnlyApi(libs.adventure.api)
    api(libs.cloud.core)
    api(libs.cloud.annotations)
    annotationProcessor(libs.cloud.annotations)
    api(libs.cloud.minecraft.extras)
    api(libs.gizmo.common)
    compileOnlyApi(libs.jspecify)
    api(project(":easyarmorstands-api"))
    api(project(":easyarmorstands-assets"))
}

tasks {
    jar {
        manifest {
            attributes["FMLModType"] = "LIBRARY"
            attributes["Automatic-Module-Name"] = "me.m56738.easyarmorstands.common"
        }
    }
}
