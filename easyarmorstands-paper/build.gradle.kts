import io.papermc.hangarpublishplugin.model.Platforms

plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.shadow)
    alias(libs.plugins.hangar.publish)
    alias(libs.plugins.run.paper)
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnlyApi(libs.checker.qual)
    compileOnlyApi(libs.apiguardian.api)
    api(project(":easyarmorstands-paper-api"))
    api(project(":easyarmorstands-common"))
    api(libs.bstats)
    api(libs.cloud.minecraft.extras)
    api(libs.cloud.paper)
    api(libs.commodore) {
        isTransitive = false
    }
    api(libs.configurate.yaml)
    api(libs.gizmo.bukkit)
}

tasks {
    runServer {
        minecraftVersion(libs.versions.minecraft.get())
    }

    processResources {
        val props = mapOf(
            "version" to version,
            "minecraftVersion" to libs.versions.minecraft.get()
        )
        inputs.properties(props)
        filesMatching("*.yml") {
            expand(props)
        }
    }

    shadowJar {
        val prefix = "me.m56738.easyarmorstands.lib"
        relocate("io.leangen.geantyref", "$prefix.geantyref")
        relocate("me.lucko.commodore", "$prefix.commodore")
        relocate("me.m56738.gizmo", "$prefix.gizmo")
        relocate("net.kyori.option", "$prefix.kyori.option")
        relocate("org.bstats", "$prefix.bstats")
        relocate("org.incendo.cloud", "$prefix.cloud")
        relocate("org.spongepowered.configurate", "$prefix.configurate")
        relocate("org.yaml.snakeyaml", "$prefix.snakeyaml")
        dependencies {
            exclude(dependency("com.google.code.gson:gson"))
        }
        mergeServiceFiles()
    }

    val staticJar by registering(Copy::class) {
        from(shadowJar)
        into(layout.buildDirectory.dir("static"))
        rename { "EasyArmorStands-Paper.jar" }
    }

    assemble {
        dependsOn(staticJar)
    }
}

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
                    platformVersions.set(listOf(libs.versions.minecraft.get()))
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
