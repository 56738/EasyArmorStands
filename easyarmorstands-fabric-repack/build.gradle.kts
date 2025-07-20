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
    compileOnly(libs.fabric.loader)
    bundle(project(":easyarmorstands-modded"))
    bundle(project(":easyarmorstands-modded-api"))
}

tasks {
    jar {
        manifest {
            attributes["Fabric-Loom-Remap"] = true
        }
    }
}
