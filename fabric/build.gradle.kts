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
}

loom {
    mods {
        register("easyarmorstands") {
            sourceSet(sourceSets.main.get())
        }
    }
}

fabricModJson {
    id = "easyarmorstands"
    name = "EasyArmorStands"
    author("56738")

}
