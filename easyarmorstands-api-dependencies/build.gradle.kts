plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish.base")
    alias(libs.plugins.shadow)
}

dependencies {
    api(libs.adventure.api)
    api(libs.adventure.text.minimessage)
    api(libs.adventure.text.serializer.plain)
    api(libs.configurate.core)
    api(libs.configurate.yaml)
    api(libs.joml) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
    }
}

tasks {
    jar {
        archiveClassifier = "dev"
    }

    shadowJar {
        val prefix = "me.m56738.easyarmorstands.lib"
        relocate("io.leangen.geantyref", "$prefix.geantyref")
        relocate("net.kyori", "$prefix.kyori")
        relocate("org.joml", "$prefix.joml")
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
