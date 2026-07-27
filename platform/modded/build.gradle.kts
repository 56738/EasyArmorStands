plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
    alias(libs.plugins.moddev)
}

dependencies {
    api(project(":easyarmorstands-platform"))
    compileOnly(libs.adventure.platform.mod.shared)
}

neoForge {
    neoFormVersion = libs.versions.neoform.get()

    accessTransformers {
        publish(file("src/main/resources/META-INF/accesstransformer.cfg"))
    }
}

tasks {
    jar {
        manifest {
            attributes("FMLModType" to "GAMELIBRARY")
            attributes("Automatic-Module-Name" to "me.m56738.easyarmorstands.platform.modded")
        }
    }
}
