import io.papermc.hangarpublishplugin.model.Platforms

plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.shadow)
    alias(libs.plugins.hangar.publish)
    alias(libs.plugins.minotaur)
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
        archiveBaseName.set("EasyArmorStands")
        archiveClassifier.set("")
        destinationDirectory.set(layout.buildDirectory)
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

val supportedGameVersions = listOf(
    "1.8.8",
    "1.8.9",
    "1.9",
    "1.9.1",
    "1.9.2",
    "1.9.3",
    "1.9.4",
    "1.10",
    "1.10.1",
    "1.10.2",
    "1.11",
    "1.11.1",
    "1.11.2",
    "1.12",
    "1.12.1",
    "1.12.2",
    "1.13",
    "1.13.1",
    "1.13.2",
    "1.14",
    "1.14.1",
    "1.14.2",
    "1.14.3",
    "1.14.4",
    "1.15",
    "1.15.1",
    "1.15.2",
    "1.16",
    "1.16.1",
    "1.16.2",
    "1.16.3",
    "1.16.4",
    "1.16.5",
    "1.17",
    "1.17.1",
    "1.18",
    "1.18.1",
    "1.18.2",
    "1.19",
    "1.19.1",
    "1.19.2",
    "1.19.3",
    "1.19.4",
    "1.20",
    "1.20.1",
    "1.20.2",
    "1.20.3",
    "1.20.4",
    "1.20.5",
    "1.20.6",
    "1.21",
    "1.21.1",
    "1.21.2",
    "1.21.3",
    "1.21.4",
    "1.21.5",
    "1.21.6",
    "1.21.7",
    "1.21.8",
    "1.21.9",
    "1.21.10",
    "1.21.11",
)

modrinth {
    projectId = "easyarmorstands"
    uploadFile.set(tasks.shadowJar)
    versionType = "release"
    changelog = provider { rootProject.file("CHANGELOG.md").readText() }
    syncBodyFrom = provider { rootProject.file("README.md").readText() }
    gameVersions = supportedGameVersions
    loaders = listOf("paper", "spigot", "bukkit")
}

hangarPublish {
    publications.register("plugin") {
        id = "EasyArmorStands"
        channel = "Release"
        changelog = provider { rootProject.file("CHANGELOG.md").readText() }
        apiKey = System.getenv("HANGAR_API_TOKEN")
        platforms {
            register(Platforms.PAPER) {
                jar = tasks.shadowJar.flatMap { it.archiveFile }
                platformVersions = supportedGameVersions
            }
        }
        pages {
            resourcePage(provider { rootProject.file("README.md").readText() })
        }
    }
}
