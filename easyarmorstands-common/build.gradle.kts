plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnlyApi(libs.adventure.api)
    compileOnlyApi(libs.cloud.core)
    compileOnlyApi(libs.gizmo.common)
    compileOnlyApi(libs.jspecify)
    api(project(":easyarmorstands-api"))
}

tasks {
    jar {
        manifest {
            attributes["FMLModType"] = "LIBRARY"
            attributes["Automatic-Module-Name"] = "me.m56738.easyarmorstands.common"
        }
    }
}
