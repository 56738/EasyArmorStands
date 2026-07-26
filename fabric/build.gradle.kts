import org.spongepowered.configurate.objectmapping.ConfigSerializable

plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.resource.factory.fabric.convention)
}

dependencies {
    api(project(":easyarmorstands-fabric-api"))
    api(project(":easyarmorstands-modded"))
    api(libs.cloud.fabric)
    api(libs.gizmo.fabric)
    minecraft(libs.minecraft)

    include(libs.adventure.platform.fabric)
    include(libs.fabric.permissions.api)
    include(libs.cloud.annotations)
    include(libs.cloud.minecraft.extras)
    include(libs.cloud.fabric)
    include(libs.gizmo.fabric)
    include(libs.configurate.core)
    include(libs.configurate.yaml)
    include(project(":"))
    include(project(":easyarmorstands-api"))
    include(project(":easyarmorstands-fabric-api"))
    include(project(":easyarmorstands-modded"))
    include(project(":easyarmorstands-modded-api"))
    include(project(":easyarmorstands-platform"))
    include(project(":easyarmorstands-platform-fabric"))
    include(project(":easyarmorstands-platform-modded"))
}

loom {
    mods {
        register("easyarmorstands") {
            sourceSet(sourceSets.main.get())
        }
    }

    runConfigs {
        named("client") {
            displayName = "Fabric Client"
            generateRunConfig = true
            appendProjectPathToDisplayName = false
        }
        named("server") {
            displayName = "Fabric Server"
            generateRunConfig = true
            appendProjectPathToDisplayName = false
        }
    }
}

tasks {
    jar {
        archiveBaseName = "EasyArmorStands-Fabric"
        destinationDirectory.set(layout.buildDirectory)
        from("../src/main/resources/assets/easyarmorstands/icon.png")
    }

    val staticJar = register<Copy>("staticJar") {
        group = "build"
        description = "Creates a jar archive with a static file name."
        from(jar)
        into(layout.buildDirectory.dir("static"))
        rename { "EasyArmorStands-Fabric.jar" }
    }

    assemble {
        dependsOn(staticJar)
    }
}

fabricModJson {
    id = "easyarmorstands"
    name = "EasyArmorStands"
    description = "Armor stand and display entity editor"
    author("56738")
    icon("icon.png")
    mainEntrypoint("me.m56738.easyarmorstands.fabric.EasyArmorStandsMod")
    mixin("easyarmorstands.mixins.json")
    license(" GPL-3.0-or-later")
    contact {
        homepage = "https://modrinth.com/mod/easyarmorstands"
        sources = "https://github.com/56738/EasyArmorStands"
        issues = "https://github.com/56738/EasyArmorStands/issues"
    }
    depends("easyarmorstands-platform-fabric", project.version.toString())
    depends("cloud", "*")
    depends("gizmo", "*")
    depends("java", ">=" + java.toolchain.languageVersion.get().asInt())
    depends("minecraft", "~" + libs.versions.minecraft.get())
    custom(
        "modmenu", complexCustomValue(
            ModMenu(
                links = mapOf(
                    "modmenu.discord" to "https://discord.gg/AgNps57FFJ"
                )
            )
        )
    )
}

@ConfigSerializable
data class ModMenu(
    @get:Input
    val links: Map<String, String>
)
