plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.m56738"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
}

dependencies {
    implementation(project(":easyarmorstands-api"))
    implementation(project(":easyarmorstands-math"))
    implementation(project(":easyarmorstands-module"))
    runtimeOnly(project(":easyarmorstands-skeleton"))
    compileOnly("org.bukkit:bukkit:1.8.8-R0.1-SNAPSHOT") {
        isTransitive = false
    }
    implementation("net.kyori:adventure-platform-bukkit:4.1.2")
    implementation("net.kyori:adventure-text-minimessage:4.11.0")
    implementation("me.lucko:commodore:2.2") {
        isTransitive = false
    }
    implementation("cloud.commandframework:cloud-paper:1.7.0")
    implementation("cloud.commandframework:cloud-minecraft-extras:1.7.0")
}

bukkit {
    main = "me.m56738.easyarmorstands.plugin.Main"
    apiVersion = "1.13"
    author = "56738"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }

    shadowJar {
        val prefix = "me.m56738.easyarmorstands.lib"
        relocate("cloud.commandframework", "$prefix.cloud")
        relocate("io.leangen.geantyref", "$prefix.geantyref")
        relocate("me.lucko.commodore", "$prefix.commodore")
        relocate("net.kyori", "$prefix.kyori")
        mergeServiceFiles()
    }
}
