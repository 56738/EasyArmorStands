plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.moddev)
}

neoForge {
    enable {
        neoFormVersion = libs.versions.neoform.get()
    }
    parchment {
        minecraftVersion = libs.versions.parchment.minecraft
        mappingsVersion = libs.versions.parchment.mappings
    }
}

dependencies {
    api(project(":easyarmorstands-modded-api"))
    api(project(":easyarmorstands-common"))
    compileOnlyApi(libs.gizmo.modded)
    compileOnlyApi(libs.cloud.minecraft.modded.common)
}

tasks {
    jar {
        manifest {
            attributes["FMLModType"] = "GAMELIBRARY"
            attributes["Automatic-Module-Name"] = "me.m56738.easyarmorstands.modded"
        }
    }
}
