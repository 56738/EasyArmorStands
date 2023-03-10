@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("java-library")
    alias(libs.plugins.shadow)
}

group = "me.m56738"
version = "1.1.0-SNAPSHOT"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
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
        val main = sourceSets.main.get()
        compileClasspath += main.output + main.runtimeClasspath
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
            isTransitive = false
        }
    }
}

fun registerAddon(name: String) {
    registerSourceSet(name)
    dependencies {
        "${name}CompileOnly"(libs.bukkit)
    }
}

registerVersion("v1_8", "org.bukkit:bukkit:1.8.8-R0.1-SNAPSHOT")
registerVersion("v1_8_spigot", "org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
registerVersion("v1_9", "org.bukkit:bukkit:1.9-R0.1-SNAPSHOT")
registerVersion("v1_11", "org.bukkit:bukkit:1.11-R0.1-SNAPSHOT")
registerVersion("v1_12_paper", "com.destroystokyo.paper:paper-api:1.12.2-R0.1-SNAPSHOT")
registerVersion("v1_13", "org.bukkit:bukkit:1.13-R0.1-SNAPSHOT")
registerVersion("v1_14", "org.bukkit:bukkit:1.14-R0.1-SNAPSHOT")
registerVersion("v1_16", "org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
registerVersion("v1_16_paper", "com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
registerVersion("v1_18", "org.spigotmc:spigot-api:1.18-R0.1-SNAPSHOT")
registerVersion("v1_18_paper", "io.papermc.paper:paper-api:1.18-R0.1-SNAPSHOT")

registerAddon("headdatabase")
registerAddon("traincarts")
registerAddon("plotsquared")

dependencies {
    "headdatabaseCompileOnly"(libs.headdatabase.api)
    "traincartsCompileOnly"(libs.traincarts)
    "plotsquaredImplementation"(platform("com.intellectualsites.bom:bom-1.18.x:1.22"))
    "plotsquaredCompileOnly"("com.plotsquared:PlotSquared-Core")
    "plotsquaredCompileOnly"("com.plotsquared:PlotSquared-Bukkit") {
        isTransitive = false
    }
}
