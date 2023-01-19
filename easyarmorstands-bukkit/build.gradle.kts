plugins {
    id("easyarmorstands.base-conventions")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

bukkit {
    name = "EasyArmorStands"
    main = "gg.bundlegroup.easyarmorstands.platform.bukkit.BukkitMain"
    apiVersion = "1.13"
    author = "56738"
}

dependencies {
    implementation(project(":easyarmorstands"))
    compileOnly(libs.bukkit) {
        isTransitive = false
    }
    implementation(libs.adventure.platform.bukkit)
    implementation(libs.cloud.paper)
    implementation(libs.commodore) {
        isTransitive = false
    }
    runtimeOnly(project(":easyarmorstands-bukkit-v1_8"))
    runtimeOnly(project(":easyarmorstands-bukkit-v1_9"))
    runtimeOnly(project(":easyarmorstands-bukkit-v1_11"))
    runtimeOnly(project(":easyarmorstands-bukkit-v1_13"))
    runtimeOnly(project(":easyarmorstands-bukkit-v1_14"))
    runtimeOnly(project(":easyarmorstands-bukkit-v1_18"))
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }

    shadowJar {
        val prefix = "gg.bundlegroup.easyarmorstands.lib"
        relocate("org.joml", "$prefix.joml")
        relocate("net.kyori", "$prefix.kyori")
        relocate("cloud.commandframework", "$prefix.cloud")
        relocate("io.leangen.geantyref", "$prefix.geantyref")
        relocate("me.lucko.commodore", "$prefix.commodore")
        mergeServiceFiles()
    }
}
