plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish.base")
    alias(libs.plugins.shadow)
}

dependencies {
    api(libs.configurate.core)
    api(libs.configurate.yaml)
}

tasks {
    jar {
        archiveClassifier = "dev"
    }

    shadowJar {
        val prefix = "me.m56738.easyarmorstands.lib"
        relocate("io.leangen.geantyref", "$prefix.geantyref")
        relocate("org.spongepowered.configurate", "$prefix.configurate")
        relocate("org.yaml.snakeyaml", "$prefix.snakeyaml")
        archiveClassifier = ""
    }
}

publishing {
    publications {
        create("maven", MavenPublication::class) {
            from(components["shadow"])
        }
    }
}
