import io.papermc.hangarpublishplugin.model.Platforms
import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml

plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.shadow)
    alias(libs.plugins.resource.factory.paper.convention)
    alias(libs.plugins.run.paper)
    alias(libs.plugins.hangar.publish)
    alias(libs.plugins.minotaur)
}

dependencies {
    api(project(":easyarmorstands-paper-api"))
    compileOnly(libs.paper.api)

    api(project(":"))

    api(libs.bstats)
    api(libs.cloud.paper)
    api(libs.gizmo.bukkit)

    runtimeOnly(project(":easyarmorstands-bentobox"))
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
    }

    jar {
        archiveClassifier = "dev"
    }

    shadowJar {
        val prefix = "me.m56738.easyarmorstands.lib"
        relocate("org.incendo.cloud", "$prefix.cloud")
        relocate("io.leangen.geantyref", "$prefix.geantyref")
        relocate("me.m56738.gizmo", "$prefix.gizmo")
        relocate("net.kyori.option", "$prefix.kyori.option")
        relocate("org.bstats", "$prefix.bstats")
        relocate("org.spongepowered.configurate", "$prefix.configurate")
        exclude("pack.mcmeta")
        mergeServiceFiles()
        archiveBaseName = "EasyArmorStands-Paper"
        archiveClassifier = ""
        destinationDirectory.set(layout.buildDirectory)
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    val staticJar = register<Copy>("staticJar") {
        group = "build"
        description = "Creates a jar archive with a static file name."
        from(shadowJar)
        into(layout.buildDirectory.dir("static"))
        rename { "EasyArmorStands-Paper.jar" }
    }

    assemble {
        dependsOn(staticJar)
    }
}

paperPluginYaml {
    apiVersion = libs.versions.minecraft
    name = "EasyArmorStands"
    main = "me.m56738.easyarmorstands.paper.Main"
    bootstrapper = "me.m56738.easyarmorstands.paper.Bootstrap"
    author = "56738"

    dependencies {
        server("BentoBox", PaperPluginYaml.Load.BEFORE, false)
        server("FancyHolograms", PaperPluginYaml.Load.BEFORE, false)
        server("GriefDefender", PaperPluginYaml.Load.BEFORE, false)
        server("HeadDatabase", PaperPluginYaml.Load.BEFORE, false)
        server("HuskClaims", PaperPluginYaml.Load.BEFORE, false)
        server("Lands", PaperPluginYaml.Load.BEFORE, false)
        server("PlotSquared", PaperPluginYaml.Load.BEFORE, false)
        server("Residence", PaperPluginYaml.Load.BEFORE, false)
        server("Towny", PaperPluginYaml.Load.BEFORE, false)
        server("Train_Carts", PaperPluginYaml.Load.BEFORE, false)
        server("ViaVersion", PaperPluginYaml.Load.BEFORE, false)
        server("WorldGuard", PaperPluginYaml.Load.BEFORE, false)
    }

    permissions {
        register("easyarmorstands.survival") {
            description = "Allow editing armor stands and their basic properties"
            children("easyarmorstands.align")
            children("easyarmorstands.clipboard")
            children("easyarmorstands.edit.armorstand")
            children("easyarmorstands.give")
            children("easyarmorstands.property.armorstand.arms")
            children("easyarmorstands.property.armorstand.baseplate")
            children("easyarmorstands.property.armorstand.cantick")
            children("easyarmorstands.property.armorstand.lock")
            children("easyarmorstands.property.armorstand.pose.body")
            children("easyarmorstands.property.armorstand.pose.head")
            children("easyarmorstands.property.armorstand.pose.leftarm")
            children("easyarmorstands.property.armorstand.pose.leftleg")
            children("easyarmorstands.property.armorstand.pose.rightarm")
            children("easyarmorstands.property.armorstand.pose.rightleg")
            children("easyarmorstands.property.armorstand.size")
            children("easyarmorstands.property.equipment.hand")
            children("easyarmorstands.property.equipment.offhand")
            children("easyarmorstands.property.equipment.feet")
            children("easyarmorstands.property.equipment.legs")
            children("easyarmorstands.property.equipment.chest")
            children("easyarmorstands.property.equipment.head")
            children("easyarmorstands.property.equipment.body")
            children("easyarmorstands.property.gravity")
            children("easyarmorstands.property.location")
            children("easyarmorstands.property.visible")
            children("easyarmorstands.select")
            children("easyarmorstands.snap")
        }

        register("easyarmorstands.creative") {
            description = "Allow spawning and editing armor stands, display entities and mannequins"
            children("easyarmorstands.survival")
            children("easyarmorstands.clone")
            children("easyarmorstands.color")
            children("easyarmorstands.convert")
            children("easyarmorstands.copy.entity")
            children("easyarmorstands.destroy.armorstand")
            children("easyarmorstands.destroy.blockdisplay")
            children("easyarmorstands.destroy.interaction")
            children("easyarmorstands.destroy.itemdisplay")
            children("easyarmorstands.destroy.mannequin")
            children("easyarmorstands.destroy.textdisplay")
            children("easyarmorstands.edit.blockdisplay")
            children("easyarmorstands.edit.interaction")
            children("easyarmorstands.edit.itemdisplay")
            children("easyarmorstands.edit.mannequin")
            children("easyarmorstands.edit.textdisplay")
            children("easyarmorstands.position")
            children("easyarmorstands.property.ai")
            children("easyarmorstands.property.armorstand.marker")
            children("easyarmorstands.property.display.billboard")
            children("easyarmorstands.property.display.block")
            children("easyarmorstands.property.display.brightness")
            children("easyarmorstands.property.display.glow.color")
            children("easyarmorstands.property.display.item")
            children("easyarmorstands.property.display.item.transform")
            children("easyarmorstands.property.display.rotation")
            children("easyarmorstands.property.display.scale")
            children("easyarmorstands.property.display.shearing")
            children("easyarmorstands.property.display.size")
            children("easyarmorstands.property.display.text")
            children("easyarmorstands.property.display.text.alignment")
            children("easyarmorstands.property.display.text.background")
            children("easyarmorstands.property.display.text.linewidth")
            children("easyarmorstands.property.display.text.seethrough")
            children("easyarmorstands.property.display.text.shadow")
            children("easyarmorstands.property.display.translation")
            children("easyarmorstands.property.glow")
            children("easyarmorstands.property.interaction.responsive")
            children("easyarmorstands.property.invulnerable")
            children("easyarmorstands.property.mannequin.description")
            children("easyarmorstands.property.mannequin.immovable")
            children("easyarmorstands.property.mannequin.mainhand")
            children("easyarmorstands.property.mannequin.part.visible.cape")
            children("easyarmorstands.property.mannequin.part.visible.hat")
            children("easyarmorstands.property.mannequin.part.visible.jacket")
            children("easyarmorstands.property.mannequin.part.visible.pants.left")
            children("easyarmorstands.property.mannequin.part.visible.pants.right")
            children("easyarmorstands.property.mannequin.part.visible.sleeve.left")
            children("easyarmorstands.property.mannequin.part.visible.sleeve.right")
            children("easyarmorstands.property.mannequin.profile")
            children("easyarmorstands.property.name")
            children("easyarmorstands.property.name.visible")
            children("easyarmorstands.property.silent")
            children("easyarmorstands.redo")
            children("easyarmorstands.spawn.armorstand")
            children("easyarmorstands.spawn.blockdisplay")
            children("easyarmorstands.spawn.interaction")
            children("easyarmorstands.spawn.itemdisplay")
            children("easyarmorstands.spawn.mannequin")
            children("easyarmorstands.spawn.textdisplay")
            children("easyarmorstands.undo")
        }
    }
}

val supportedGameVersions = listOf(
    "26.2",
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
                platformVersions = supportedGameVersions
            }
        }
        pages {
            resourcePage(provider { rootProject.file("README.md").readText() })
        }
    }
}
