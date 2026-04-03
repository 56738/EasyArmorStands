plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.shadow)
}

dependencies {
    api(libs.bstats)
    api(libs.cloud.annotations)
    api(libs.cloud.minecraft.extras)
    api(libs.cloud.paper)
    api(libs.commodore) {
        isTransitive = false
    }
    api(libs.configurate.yaml)
    api(libs.gizmo.bukkit)
    api(libs.item.nbt.api)
}

tasks {
    shadowJar {
        val prefix = "me.m56738.easyarmorstands.lib"
        relocate("org.incendo.cloud", "$prefix.cloud")
        relocate("io.leangen.geantyref", "$prefix.geantyref")
        relocate("me.lucko.commodore", "$prefix.commodore")
        relocate("me.m56738.gizmo", "$prefix.gizmo")
        relocate("org.bstats", "$prefix.bstats")
        relocate("org.spongepowered.configurate", "$prefix.configurate")
        relocate("org.yaml.snakeyaml", "$prefix.snakeyaml")
        relocate("de.tr7zw.changeme.nbtapi", "$prefix.nbtapi")
        dependencies {
            exclude(dependency("com.google.code.gson:gson"))
        }
        mergeServiceFiles()
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}
