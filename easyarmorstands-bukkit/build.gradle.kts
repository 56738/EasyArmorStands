plugins {
    id("easyarmorstands.base-conventions")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

bukkit {
    name = "EasyArmorStands"
    main = "gg.bundlegroup.easyarmorstands.bukkit.EasyArmorStands"
    apiVersion = "1.13"
    author = "56738"
    softDepend = listOf("HeadDatabase", "Train_Carts")
}

dependencies {
    api(project(":easyarmorstands-common"))
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
    runtimeOnly(project(":easyarmorstands-bukkit-traincarts"))
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
