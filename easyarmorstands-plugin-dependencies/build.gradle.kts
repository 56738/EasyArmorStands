plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.shadow)
}

dependencies {
    api(libs.adventure.platform.bukkit)
    api(libs.adventure.text.minimessage)
    api(libs.adventure.text.serializer.gson)
    api(libs.adventure.text.serializer.legacy)
    api(libs.adventure.text.serializer.plain)
    api(libs.bstats)
    api(libs.cloud.annotations)
    api(libs.cloud.minecraft.extras)
    api(libs.cloud.paper)
    api(libs.commodore) {
        isTransitive = false
    }
    api(libs.configurate.yaml)
    api(libs.joml) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
    }
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
        relocate("net.kyori", "$prefix.kyori")
        relocate("org.bstats", "$prefix.bstats")
        relocate("org.joml", "$prefix.joml")
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
