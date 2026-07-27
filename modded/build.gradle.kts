plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.moddev)
}

dependencies {
    api(project(":easyarmorstands-modded-api"))

    api(project(":"))

    api(libs.cloud.minecraft.modded.common)
    api(libs.gizmo.modded.common)
}

neoForge {
    neoFormVersion = libs.versions.neoform.get()
}

tasks {
    jar {
        manifest {
            attributes("FMLModType" to "GAMELIBRARY")
            attributes("Automatic-Module-Name" to "me.m56738.easyarmorstands.modded")
        }
    }
}
