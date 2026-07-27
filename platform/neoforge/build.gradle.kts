plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
    alias(libs.plugins.moddev)
}

dependencies {
    api(project(":easyarmorstands-platform-modded"))
    api(libs.adventure.platform.neoforge)
}

neoForge {
    version = libs.versions.neoforge.get()
}

tasks {
    jar {
        manifest {
            attributes("FMLModType" to "GAMELIBRARY")
            attributes("Automatic-Module-Name" to "me.m56738.easyarmorstands.platform.neoforge")
        }
    }
}
