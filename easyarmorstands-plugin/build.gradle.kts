import io.papermc.hangarpublishplugin.model.Platforms

plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.shadow)
    alias(libs.plugins.hangar.publish)
    alias(libs.plugins.run.paper)
}

dependencies {
    compileOnly(libs.bukkit)
    compileOnlyApi(libs.jetbrains.annotations)
    compileOnlyApi(libs.checker.qual)
    api(project(":easyarmorstands-api"))
    api(project(":easyarmorstands-assets"))
    api(project(":easyarmorstands-plugin-dependencies", configuration = "shadow"))
    runtimeOnly(project(":easyarmorstands-bentobox"))
    runtimeOnly(project(":easyarmorstands-display"))
    runtimeOnly(project(":easyarmorstands-fancyholograms"))
    runtimeOnly(project(":easyarmorstands-griefdefender"))
    runtimeOnly(project(":easyarmorstands-griefprevention"))
    runtimeOnly(project(":easyarmorstands-headdatabase"))
    runtimeOnly(project(":easyarmorstands-huskclaims"))
    runtimeOnly(project(":easyarmorstands-lands"))
    runtimeOnly(project(":easyarmorstands-plotsquared"))
    runtimeOnly(project(":easyarmorstands-residence"))
    runtimeOnly(project(":easyarmorstands-towny"))
    runtimeOnly(project(":easyarmorstands-traincarts"))
    runtimeOnly(project(":easyarmorstands-worldguard-v6"))
    runtimeOnly(project(":easyarmorstands-worldguard-v7"))
}

tasks {
    runServer {
        minecraftVersion("1.21.8")
        javaLauncher = project.javaToolchains.launcherFor {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    processResources {
        inputs.property("version", version)
        filesMatching("*.yml") {
            expand("version" to version)
        }
    }

    shadowJar {
        exclude("pack.mcmeta")
        mergeServiceFiles()
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    val staticJar by registering(Copy::class) {
        from(shadowJar)
        into(layout.buildDirectory.dir("static"))
        rename { "EasyArmorStands.jar" }
    }

    assemble {
        dependsOn(staticJar)
    }
}

fun registerSourceSet(name: String) {
    val sourceSet = sourceSets.register(name) {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }

    configurations.named("${name}CompileOnly") {
        extendsFrom(configurations.compileOnlyApi.get())
    }

    configurations.named("${name}Implementation") {
        extendsFrom(configurations.implementation.get())
    }

    tasks {
        shadowJar {
            from(sourceSet.map { it.output })
            archiveBaseName.set("EasyArmorStands")
            archiveClassifier.set("")
            destinationDirectory.set(layout.buildDirectory)
        }
    }
}

fun registerVersion(name: String, api: String) {
    registerSourceSet(name)
    dependencies {
        "${name}CompileOnly"(api)
    }
}

registerVersion("v1_8", "org.bukkit:bukkit:1.8.8-R0.1-SNAPSHOT")
registerVersion("v1_9", "org.bukkit:bukkit:1.9-R0.1-SNAPSHOT")
registerVersion("v1_9_spigot", "org.spigotmc:spigot-api:1.9.4-R0.1-SNAPSHOT")
registerVersion("v1_10_2", "org.bukkit:bukkit:1.10.2-R0.1-SNAPSHOT")
registerVersion("v1_11", "org.bukkit:bukkit:1.11-R0.1-SNAPSHOT")
registerVersion("v1_12", "org.bukkit:bukkit:1.12-R0.1-SNAPSHOT")
registerVersion("v1_12_paper", "com.destroystokyo.paper:paper-api:1.12.2-R0.1-SNAPSHOT")
registerVersion("v1_13", "org.bukkit:bukkit:1.13-R0.1-SNAPSHOT")
registerVersion("v1_14", "org.bukkit:bukkit:1.14-R0.1-SNAPSHOT")
registerVersion("v1_15_2", "org.bukkit:bukkit:1.15.2-R0.1-SNAPSHOT")
registerVersion("v1_16", "org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
registerVersion("v1_16_paper", "com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
registerVersion("v1_18", "org.spigotmc:spigot-api:1.18-R0.1-SNAPSHOT")
registerVersion("v1_18_paper", "io.papermc.paper:paper-api:1.18-R0.1-SNAPSHOT")
registerVersion("v1_19_4", "org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
registerVersion("v1_20_2", "org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
registerVersion("v1_20_6", "org.spigotmc:spigot-api:1.20.6-R0.1-SNAPSHOT")
registerVersion("v1_20_6_paper", "io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
registerVersion("v1_21_10_spigot", "org.spigotmc:spigot-api:1.21.10-R0.1-SNAPSHOT")
registerVersion("v1_21_10_paper", "io.papermc.paper:paper-api:1.21.10-R0.1-SNAPSHOT")

hangarPublish {
    val versionString = project.version.toString()
    if (versionString.endsWith("-SNAPSHOT")) {
        val build = System.getenv("GITHUB_RUN_NUMBER")

        publications.register("plugin") {
            id.set("EasyArmorStands")
            apiKey.set(System.getenv("HANGAR_API_TOKEN"))
            version.set("$versionString+$build")
            channel.set("Snapshot")
            platforms {
                register(Platforms.PAPER) {
                    jar.set(tasks.shadowJar.flatMap { it.archiveFile })
                    platformVersions.set(
                        property("minecraftVersion").toString()
                            .split(",")
                            .map { it.trim() }
                    )
                    dependencies {
                        url("HeadDatabase", "https://www.spigotmc.org/resources/head-database.14280/") {
                            required.set(false)
                        }
                        url("PlotSquared", "https://www.spigotmc.org/resources/plotsquared-v7.77506/") {
                            required.set(false)
                        }
                        url("WorldGuard", "https://enginehub.org/worldguard") {
                            required.set(false)
                        }
                        url("TrainCarts", "https://www.spigotmc.org/resources/traincarts.39592/") {
                            required.set(false)
                        }
                    }
                }
            }
        }
    }
}
