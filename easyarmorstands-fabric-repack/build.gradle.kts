plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.fabric.loom)
}

val repacked: Configuration by configurations.creating {
    isTransitive = false
}

dependencies {
    minecraft(libs.minecraft)
    mappings(@Suppress("UnstableApiUsage") loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${libs.versions.parchment.minecraft.get()}:${libs.versions.parchment.mappings.get()}@zip")
    })
    compileOnly(libs.fabric.loader)
    repacked(project(":easyarmorstands-modded"))
    repacked(project(":easyarmorstands-modded-api"))
}

tasks {
    jar {
        dependsOn(repacked)
        from(provider { repacked.map { zipTree(it) } }) {
            exclude("META-INF/MANIFEST.MF")
        }
        manifest {
            attributes["Fabric-Loom-Remap"] = true
        }
    }
}
