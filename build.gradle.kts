import org.gradle.api.credentials.PasswordCredentials

plugins {
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.plugin.yml)
    alias(libs.plugins.shadow)
}

group = "me.m56738"
version = "1.2.0-SNAPSHOT"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://ci.mg-dev.eu/plugin/repository/everything/") {
        content {
            includeGroupByRegex("com\\.bergerkiller(\\..*)?")
        }
    }
}

dependencies {
    compileOnly(libs.bukkit)
    implementation(libs.adventure.platform.bukkit)
    implementation(libs.adventure.text.minimessage)
    implementation(libs.adventure.text.serializer.gson)
    implementation(libs.adventure.text.serializer.legacy)
    implementation(libs.bstats)
    implementation(libs.cloud.annotations)
    implementation(libs.cloud.minecraft.extras)
    implementation(libs.cloud.paper)
    implementation(libs.joml)
}

tasks {
    withType<JavaCompile>().configureEach {
        options.release.set(8)
        options.encoding = "UTF-8"
    }

    withType<AbstractArchiveTask>().configureEach {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }

    assemble {
        dependsOn(shadowJar)
    }

    shadowJar {
        val prefix = "me.m56738.easyarmorstands.lib"
        relocate("org.joml", "$prefix.joml")
        relocate("net.kyori", "$prefix.kyori")
        relocate("cloud.commandframework", "$prefix.cloud")
        relocate("io.leangen.geantyref", "$prefix.geantyref")
        relocate("me.lucko.commodore", "$prefix.commodore")
        relocate("org.bstats", "$prefix.bstats")
        dependencies {
            exclude(dependency("com.google.code.gson:gson"))
        }
        mergeServiceFiles()
    }
}

java {
    disableAutoTargetJvm()
}

fun registerSourceSet(name: String) {
    val sourceSet = sourceSets.register(name) {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }

    configurations.named("${name}Implementation") {
        extendsFrom(configurations.implementation.get())
    }

    tasks {
        shadowJar {
            from(sourceSet.map { it.output })
        }
    }
}

fun registerVersion(name: String, api: String) {
    registerSourceSet(name)
    dependencies {
        "${name}CompileOnly"(api) {
            exclude("net.kyori", "adventure-api")
        }
    }
}

fun registerAddon(name: String, api: Any = libs.bukkit) {
    registerSourceSet(name)
    dependencies {
        "${name}CompileOnly"(api)
    }
}

registerVersion("v1_8", "org.bukkit:bukkit:1.8.8-R0.1-SNAPSHOT")
registerVersion("v1_8_spigot", "org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
registerVersion("v1_9", "org.bukkit:bukkit:1.9-R0.1-SNAPSHOT")
registerVersion("v1_11", "org.bukkit:bukkit:1.11-R0.1-SNAPSHOT")
registerVersion("v1_12", "org.bukkit:bukkit:1.12-R0.1-SNAPSHOT")
registerVersion("v1_12_paper", "com.destroystokyo.paper:paper-api:1.12.2-R0.1-SNAPSHOT")
registerVersion("v1_13", "org.bukkit:bukkit:1.13-R0.1-SNAPSHOT")
registerVersion("v1_13_2", "org.bukkit:bukkit:1.13.2-R0.1-SNAPSHOT")
registerVersion("v1_14", "org.bukkit:bukkit:1.14-R0.1-SNAPSHOT")
registerVersion("v1_16", "org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
registerVersion("v1_16_paper", "com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
registerVersion("v1_18", "org.spigotmc:spigot-api:1.18-R0.1-SNAPSHOT")
registerVersion("v1_18_paper", "io.papermc.paper:paper-api:1.18-R0.1-SNAPSHOT")
registerVersion("v1_19_4", "org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")

registerAddon("headdatabase")
registerAddon("plotsquared")
registerAddon("traincarts")
registerAddon("worldguard_v6")
registerAddon("worldguard_v7", "org.bukkit:bukkit:1.13-R0.1-SNAPSHOT")

dependencies {
    "headdatabaseCompileOnly"(libs.headdatabase.api)
    "plotsquaredImplementation"(platform("com.intellectualsites.bom:bom-1.18.x:1.27"))
    "plotsquaredCompileOnly"("com.plotsquared:PlotSquared-Core") {
        exclude("net.kyori", "adventure-api")
    }
    "plotsquaredCompileOnly"("com.plotsquared:PlotSquared-Bukkit") {
        isTransitive = false
    }
    "traincartsCompileOnly"(libs.traincarts)
    "worldguard_v6CompileOnly"(libs.worldguard.v6)
    "worldguard_v7CompileOnly"(libs.worldguard.v7)
}

bukkit {
    name = "EasyArmorStands"
    main = "me.m56738.easyarmorstands.EasyArmorStands"
    apiVersion = "1.13"
    author = "56738"
    softDepend = listOf("HeadDatabase", "PlotSquared", "Train_Carts", "WorldGuard")
    permissions {
        create("easyarmorstands.creative") {
            description = "Allows using EasyArmorStands features which are safe for creative mode players."
            children = listOf(
                    "easyarmorstands.clone",
                    "easyarmorstands.color",
                    "easyarmorstands.destroy",
                    "easyarmorstands.give",
                    "easyarmorstands.history",
                    "easyarmorstands.set.display.block",
                    "easyarmorstands.set.display.item",
                    "easyarmorstands.spawn",
                    "easyarmorstands.survival",
                    "easyarmorstands.traincarts.model"
            )
        }
        create("easyarmorstands.survival") {
            description = "Allows using EasyArmorStands features which are safe for survival mode players."
            children = listOf(
                    "easyarmorstands.align",
                    "easyarmorstands.edit",
                    "easyarmorstands.help",
                    "easyarmorstands.open",
                    "easyarmorstands.property.arms",
                    "easyarmorstands.property.baseplate",
                    "easyarmorstands.property.cantick",
                    "easyarmorstands.property.equipment",
                    "easyarmorstands.property.glow",
                    "easyarmorstands.property.gravity",
                    "easyarmorstands.property.invulnerable",
                    "easyarmorstands.property.lock",
                    "easyarmorstands.property.marker",
                    "easyarmorstands.property.name",
                    "easyarmorstands.property.size",
                    "easyarmorstands.property.visible",
                    "easyarmorstands.snap",
                    "easyarmorstands.version",
            )

        }
        create("easyarmorstands.align") {
            description = "Allows using /eas align to move an armor stand to the center of its block."
        }
        create("easyarmorstands.clone") {
            description = "Allows cloning armor stands."
        }
        create("easyarmorstands.color") {
            description = "Allows using the color picker."
        }
        create("easyarmorstands.debug") {
            description = "Allows viewing debug information."
        }
        create("easyarmorstands.destroy") {
            description = "Allows destroying selected armor stands."
        }
        create("easyarmorstands.edit") {
            description = "Allows editing armor stands. Required to use this plugin."
        }
        create("easyarmorstands.give") {
            description = "Allows giving yourself the EasyArmorStand tool."
        }
        create("easyarmorstands.help") {
            description = "Allows viewing the help menu."
        }
        create("easyarmorstands.history") {
            description = "Allows undoing and redoing changes."
        }
        create("easyarmorstands.open") {
            description = "Allows opening the EasyArmorStands menu."
        }
        create("easyarmorstands.plotsquared.bypass") {
            description = "Allows bypassing PlotSquared restrictions."
        }
        create("easyarmorstands.property.arms") {
            description = "Allows toggling armor stand arm visibility."
        }
        create("easyarmorstands.property.baseplate") {
            description = "Allows toggling armor stand base plate visibility."
        }
        create("easyarmorstands.property.cantick") {
            description = "Allows toggling whether armor stand ticking is disabled (Paper only)."
        }
        create("easyarmorstands.property.equipment") {
            description = "Allows modifying armor stand equipment."
        }
        create("easyarmorstands.property.glow") {
            description = "Allows toggling glowing armor stand outlines."
        }
        create("easyarmorstands.property.gravity") {
            description = "Allows toggling gravity for an armor stand."
        }
        create("easyarmorstands.property.invulnerable") {
            description = "Allows toggling armor stand invulnerability."
        }
        create("easyarmorstands.property.lock") {
            description = "Allows toggling armor stand equipment lock."
        }
        create("easyarmorstands.property.marker") {
            description = "Allows toggling whether an armor stand is a marker."
        }
        create("easyarmorstands.property.name") {
            description = "Allows editing armor stan name tags."
        }
        create("easyarmorstands.property.size") {
            description = "Allows toggling the size of an armor stand."
        }
        create("easyarmorstands.property.visible") {
            description = "Allows toggling armor stand visibility."
        }
        create("easyarmorstands.set.display.block") {
            description = "Allows setting the block on a display entity using a command."
        }
        create("easyarmorstands.set.display.item") {
            description = "Allows setting the item on a display entity using a command."
        }
        create("easyarmorstands.snap") {
            description = "Allows toggling position and angle snapping and configuring the increment."
        }
        create("easyarmorstands.spawn") {
            description = "Allows spawning armor stands."
        }
        create("easyarmorstands.traincarts.model") {
            description = "Allows opening the TrainCarts model browser."
        }
        create("easyarmorstands.version") {
            description = "Allows viewing version information."
        }
        create("easyarmorstands.worldguard.bypass") {
            description = "Allows bypassing WorldGuard restrictions."
        }
    }
}

publishing {
    repositories {
        val snapshotUrl = "https://repo.bundlegroup.gg/repository/maven-snapshots/"
        val releaseUrl = "https://repo.bundlegroup.gg/repository/maven-releases/"
        maven(if (version.toString().endsWith("-SNAPSHOT")) snapshotUrl else releaseUrl) {
            name = "bundlegroup"
            credentials(PasswordCredentials::class)
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
