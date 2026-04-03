import io.papermc.hangarpublishplugin.model.Platforms

plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.shadow)
    alias(libs.plugins.hangar.publish)
    alias(libs.plugins.minotaur)
    alias(libs.plugins.run.paper)
}

dependencies {
    compileOnly(libs.paper.api)
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
    runtimeOnly(project(":easyarmorstands-worldguard"))
}

tasks {
    runServer {
        minecraftVersion(libs.versions.minecraft.get())
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

val supportedGameVersions = listOf(
    "1.21.11",
)

modrinth {
    projectId = "easyarmorstands"
    uploadFile.set(tasks.shadowJar)
    versionType = "release"
    changelog = provider { rootProject.file("CHANGELOG.md").readText() }
    syncBodyFrom = provider { rootProject.file("README.md").readText() }
    gameVersions = supportedGameVersions
    loaders = listOf("paper")
}

hangarPublish {
    publications.register("plugin") {
        id = "EasyArmorStands"
        channel = "Release"
        version = project.version.toString()
        changelog = provider { rootProject.file("CHANGELOG.md").readText() }
        apiKey = System.getenv("HANGAR_API_TOKEN")
        platforms {
            register(Platforms.PAPER) {
                jar = tasks.shadowJar.flatMap { it.archiveFile }
                platformVersions = supportedGameVersions - listOf("1.8.9")
            }
        }
        pages {
            resourcePage(provider { rootProject.file("README.md").readText() })
        }
    }
}
