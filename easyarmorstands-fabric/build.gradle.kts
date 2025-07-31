plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.bundle")
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

    modImplementation(libs.adventure.platform.fabric)
    modImplementation(libs.cloud.fabric)
    modImplementation(libs.fabric.api)
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.permissions.api)
    modImplementation(libs.gizmo.fabric)

    bundle(project(":easyarmorstands-assets"))
    include(project(":easyarmorstands-fabric-api"))
    include(project(":easyarmorstands-fabric-repack"))
    include(project(":easyarmorstands-common"))
    include(project(":easyarmorstands-api"))
    include(libs.adventure.platform.fabric)
    include(libs.cloud.annotations)
    include(libs.cloud.fabric)
    include(libs.cloud.minecraft.extras)
    include(libs.fabric.permissions.api)
    include(libs.gizmo.fabric)
}

loom {
    splitEnvironmentSourceSets()
    accessWidenerPath = file("src/main/resources/easyarmorstands.accesswidener")
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

    jar {
        manifest {
            attributes["Automatic-Module-Name"] = "me.m56738.easyarmorstands.fabric"
        }
    }

    val staticJar by registering(Copy::class) {
        from(remapJar)
        into(layout.buildDirectory.dir("static"))
        rename { "EasyArmorStands-Fabric.jar" }
    }

    assemble {
        dependsOn(staticJar)
    }
}
