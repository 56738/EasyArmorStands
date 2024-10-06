plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
    alias(libs.plugins.shadow)
}

dependencies {
    compileOnly(libs.bukkit)
    api(libs.adventure.api)
    api(libs.adventure.text.minimessage)
    api(libs.configurate.core)
    api(libs.configurate.yaml)
    api(libs.joml) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
    }
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }

    processResources {
        inputs.property("version", version)
        filesMatching("*.yml") {
            expand("version" to version)
        }
    }

    shadowJar {
        val prefix = "me.m56738.easyarmorstands.lib"
        relocate("io.leangen.geantyref", "$prefix.geantyref")
        relocate("net.kyori", "$prefix.kyori")
        relocate("org.joml", "$prefix.joml")
        relocate("org.spongepowered.configurate", "$prefix.configurate")
        relocate("org.yaml.snakeyaml", "$prefix.snakeyaml")
    }
}
