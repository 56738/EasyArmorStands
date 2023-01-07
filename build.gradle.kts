plugins {
    id("easyarmorstands.bukkit-conventions")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    implementation(project(":easyarmorstands-api"))
    implementation(project(":easyarmorstands-math"))
    implementation(project(":easyarmorstands-module"))
    runtimeOnly(project(":easyarmorstands-skeleton"))
    implementation("net.kyori:adventure-platform-bukkit:4.2.0")
    implementation("net.kyori:adventure-text-minimessage:4.12.0")
    implementation("me.lucko:commodore:2.2") {
        isTransitive = false
    }
    implementation("cloud.commandframework:cloud-paper:1.8.0")
    implementation("cloud.commandframework:cloud-minecraft-extras:1.8.0")
}

bukkit {
    main = "gg.bundlegroup.easyarmorstands.plugin.Main"
    apiVersion = "1.13"
    author = "56738"
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }

    shadowJar {
        val prefix = "gg.bundlegroup.easyarmorstands.lib"
        relocate("cloud.commandframework", "$prefix.cloud")
        relocate("io.leangen.geantyref", "$prefix.geantyref")
        relocate("me.lucko.commodore", "$prefix.commodore")
        relocate("net.kyori", "$prefix.kyori")
        mergeServiceFiles()
    }
}
