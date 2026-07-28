plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.shadow)
}

dependencies {
    api(libs.configurate.yaml) {
        exclude("io.leangen.geantyref")
    }
}

tasks {
    shadowJar {
        val prefix = "me.m56738.easyarmorstands.lib"
        relocate("net.kyori.option", "$prefix.kyori.option")
        relocate("org.spongepowered.configurate", "$prefix.configurate")
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        mergeServiceFiles()
    }
}
