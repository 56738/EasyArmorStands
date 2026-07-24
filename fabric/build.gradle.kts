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

    include(libs.fabric.permissions.api)
    include(libs.cloud.annotations)
    include(libs.cloud.minecraft.extras)
    include(libs.cloud.fabric)
    include(libs.gizmo.fabric)
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
    accessWidenerPath = file("src/main/resources/easyarmorstands.classtweaker")

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

fabricModJson {
    id = "easyarmorstands"
    name = "EasyArmorStands"
    author("56738")
    icon("assets/easyarmorstands/icon.png")
    mainEntrypoint("me.m56738.easyarmorstands.fabric.EasyArmorStandsMod")
    mixin("easyarmorstands.mixins.json")
    accessWidener = "easyarmorstands.classtweaker"
    depends("adventure-platform-fabric", "*")
    depends("cloud", "*")
    depends("fabric-api", "*")
    depends("fabric-permissions-api-v0", "*")
    depends("fabricloader", ">=" + libs.versions.fabric.loader.get())
    depends("gizmo", "*")
    depends("java", ">=" + java.toolchain.languageVersion.get().asInt())
    depends("minecraft", "~" + libs.versions.minecraft.get())
}
