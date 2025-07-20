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
    api(project(":easyarmorstands-api"))
    compileOnlyApi(libs.adventure.platform.mod.shared)
    compileOnlyApi(libs.jspecify)
}

tasks {
    jar {
        manifest {
            attributes["FMLModType"] = "GAMELIBRARY"
            attributes["Automatic-Module-Name"] = "me.m56738.easyarmorstands.modded.api"
        }
    }
}
