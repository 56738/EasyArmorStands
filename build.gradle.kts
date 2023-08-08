import org.gradle.api.credentials.PasswordCredentials

plugins {
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.shadow)
}

group = "me.m56738"
version = "1.3.1-SNAPSHOT"

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

    processResources {
        inputs.property("version", version)
        filesMatching("*.yml") {
            expand("version" to version)
        }
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
registerVersion("v1_19_4_paper", "io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")

registerAddon("headdatabase")
registerAddon("plotsquared")
registerAddon("traincarts")
registerAddon("worldguard_v6")
registerAddon("worldguard_v7", "org.bukkit:bukkit:1.13-R0.1-SNAPSHOT")

sourceSets {
    named("v1_19_4_paper") {
        compileClasspath += named("v1_19_4").get().output
        runtimeClasspath += named("v1_19_4").get().output
        compileClasspath += named("v1_16_paper").get().output
        runtimeClasspath += named("v1_16_paper").get().output
    }
}

dependencies {
    "headdatabaseCompileOnly"(libs.headdatabase.api)
    "plotsquaredImplementation"(platform("com.intellectualsites.bom:bom-1.18.x:1.30"))
    "plotsquaredCompileOnly"("com.plotsquared:PlotSquared-Core") {
        exclude("net.kyori", "adventure-api")
    }
    "plotsquaredCompileOnly"("com.plotsquared:PlotSquared-Bukkit") {
        isTransitive = false
    }
    "traincartsCompileOnly"(libs.traincarts)
    "worldguard_v6CompileOnly"(libs.worldguard.v6)
    "worldguard_v7CompileOnly"(libs.worldguard.v7)
    "v1_19_4CompileOnly"(libs.brigadier)
}

publishing {
    repositories {
        val snapshotUrl = "https://repo.bundlegroup.gg/snapshots"
        val releaseUrl = "https://repo.bundlegroup.gg/releases"
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
