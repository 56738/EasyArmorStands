plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.fabric.loom)
}

dependencies {
    minecraft(libs.minecraft)
    mappings(@Suppress("UnstableApiUsage") loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${libs.versions.parchment.minecraft.get()}:${libs.versions.parchment.mappings.get()}@zip")
    })

    implementation(project(":easyarmorstands-modded"))
    implementation(project(":easyarmorstands-fabric-api"))

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modImplementation(libs.cloud.fabric)
    modImplementation(libs.adventure.platform.fabric)

    include(project(":easyarmorstands-fabric-api"))
    include(project(":easyarmorstands-fabric-repack"))
    include(project(":easyarmorstands-common"))
    include(project(":easyarmorstands-api"))
    include(libs.cloud.fabric)
    include(libs.adventure.platform.fabric)
}

loom {
    splitEnvironmentSourceSets()
    mods {
        register("easyarmorstands") {
            sourceSet("main")
            sourceSet("client")
        }
    }
}

tasks {
    processResources {
        val props = mapOf("version" to project.version)
        inputs.properties(props)
        filesMatching("fabric.mod.json") {
            expand(props)
        }
    }
}
