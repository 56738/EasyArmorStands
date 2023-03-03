plugins {
    id("easyarmorstands.base-conventions")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    api(project(":easyarmorstands-core"))
    compileOnly(libs.bukkit) {
        isTransitive = false
    }
    implementation(libs.adventure.platform.bukkit)
    implementation(libs.cloud.paper)
    implementation(libs.commodore) {
        isTransitive = false
    }
    runtimeOnly(project(":easyarmorstands-bukkit-headdatabase"))
    runtimeOnly(project(":easyarmorstands-bukkit-paper"))
    runtimeOnly(project(":easyarmorstands-bukkit-plotsquared"))
    runtimeOnly(project(":easyarmorstands-bukkit-traincarts"))
    runtimeOnly(project(":easyarmorstands-bukkit-v1_8"))
    runtimeOnly(project(":easyarmorstands-bukkit-v1_9"))
    runtimeOnly(project(":easyarmorstands-bukkit-v1_11"))
    runtimeOnly(project(":easyarmorstands-bukkit-v1_13"))
    runtimeOnly(project(":easyarmorstands-bukkit-v1_14"))
    runtimeOnly(project(":easyarmorstands-bukkit-v1_16"))
    runtimeOnly(project(":easyarmorstands-bukkit-v1_18"))
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }

    shadowJar {
        val prefix = "me.m56738.easyarmorstands.lib"
        relocate("org.joml", "$prefix.joml")
        relocate("net.kyori", "$prefix.kyori")
        relocate("cloud.commandframework", "$prefix.cloud")
        relocate("io.leangen.geantyref", "$prefix.geantyref")
        relocate("me.lucko.commodore", "$prefix.commodore")
        mergeServiceFiles()
    }
}

bukkit {
    name = "EasyArmorStands"
    main = "me.m56738.easyarmorstands.bukkit.EasyArmorStands"
    apiVersion = "1.13"
    author = "56738"
    softDepend = listOf("HeadDatabase", "PlotSquared", "Train_Carts")
    permissions {
        create("easyarmorstands.creative") {
            description = "Allows using EasyArmorStands features which are safe for creative mode players."
            children = listOf(
                "easyarmorstands.clone",
                "easyarmorstands.give",
                "easyarmorstands.spawn",
                "easyarmorstands.survival"
            )
        }
        create("easyarmorstands.survival") {
            description = "Allows using EasyArmorStands features which are safe for survival mode players."
            children = listOf(
                "easyarmorstands.align",
                "easyarmorstands.edit",
                "easyarmorstands.help",
                "easyarmorstands.open",
                "easyarmorstands.property.arms",
                "easyarmorstands.property.baseplate",
                "easyarmorstands.property.cantick",
                "easyarmorstands.property.equipment",
                "easyarmorstands.property.glow",
                "easyarmorstands.property.gravity",
                "easyarmorstands.property.invulnerable",
                "easyarmorstands.property.lock",
                "easyarmorstands.property.name",
                "easyarmorstands.property.size",
                "easyarmorstands.property.visible",
                "easyarmorstands.snap",
            )

        }
        create("easyarmorstands.align") {
            description = "Allows using /eas align to move an armor stand to the center of its block."
        }
        create("easyarmorstands.clone") {
            description = "Allows cloning armor stands."
        }
        create("easyarmorstands.plotsquared.bypass") {
            description = "Allows bypassing PlotSquared restrictions."
        }
        create("easyarmorstands.property.arms") {
            description = "Allows toggling armor stand arm visibility."
        }
        create("easyarmorstands.property.baseplate") {
            description = "Allows toggling armor stand base plate visibility."
        }
        create("easyarmorstands.property.cantick") {
            description = "Allows toggling whether armor stand ticking is disabled (Paper only)."
        }
        create("easyarmorstands.property.equipment") {
            description = "Allows modifying armor stand equipment."
        }
        create("easyarmorstands.property.glow") {
            description = "Allows toggling glowing armor stand outlines."
        }
        create("easyarmorstands.property.gravity") {
            description = "Allows toggling gravity for an armor stand."
        }
        create("easyarmorstands.property.invulnerable") {
            description = "Allows toggling armor stand invulnerability."
        }
        create("easyarmorstands.property.lock") {
            description = "Allows toggling armor stand equipment lock."
        }
        create("easyarmorstands.property.name") {
            description = "Allows editing armor stan name tags."
        }
        create("easyarmorstands.property.size") {
            description = "Allows toggling the size of an armor stand."
        }
        create("easyarmorstands.property.visible") {
            description = "Allows toggling armor stand visibility."
        }
        create("easyarmorstands.edit") {
            description = "Allows editing armor stands. Required to use this plugin."
        }
        create("easyarmorstands.give") {
            description = "Allows giving yourself the EasyArmorStand tool."
        }
        create("easyarmorstands.help") {
            description = "Allows viewing the help menu."
        }
        create("easyarmorstands.open") {
            description = "Allows opening the EasyArmorStands menu."
        }
        create("easyarmorstands.snap") {
            description = "Allows toggling position and angle snapping and configuring the increment."
        }
        create("easyarmorstands.spawn") {
            description = "Allows spawning armor stands."
        }
        create("easyarmorstands.traincarts.model") {
            description = "Allows opening the TrainCarts model browser."
        }
    }
}
