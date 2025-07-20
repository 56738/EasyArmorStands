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

    api(project(":easyarmorstands-modded-api"))
}

loom {
    splitEnvironmentSourceSets()
}
