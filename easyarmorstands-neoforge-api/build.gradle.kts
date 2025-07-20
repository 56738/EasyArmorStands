plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.moddev)
}

neoForge {
    enable {
        version = libs.versions.neoforge.get()
    }
    parchment {
        minecraftVersion = libs.versions.parchment.minecraft
        mappingsVersion = libs.versions.parchment.mappings
    }
}

dependencies {
    api(project(":easyarmorstands-modded-api"))
    compileOnly(libs.neoforge)
}

tasks {
    jar {
        manifest {
            attributes["FMLModType"] = "GAMELIBRARY"
            attributes["Automatic-Module-Name"] = "me.m56738.easyarmorstands.neoforge.api"
        }
    }
}
