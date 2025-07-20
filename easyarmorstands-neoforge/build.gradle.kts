plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.bundle")
    alias(libs.plugins.moddev)
}

neoForge {
    validateAccessTransformers = true
    enable {
        version = libs.versions.neoforge.get()
    }
    parchment {
        minecraftVersion = libs.versions.parchment.minecraft
        mappingsVersion = libs.versions.parchment.mappings
    }
    mods {
        register("easyarmorstands") {
            sourceSet(sourceSets["main"])
        }
    }
    runs {
        register("client") {
            client()
        }
        register("server") {
            server()
        }
    }
}

dependencies {
    implementation(project(":easyarmorstands-modded"))
    implementation(project(":easyarmorstands-neoforge-api"))
    implementation(libs.neoforge)
    implementation(libs.cloud.neoforge)
    implementation(libs.adventure.platform.neoforge)

    bundle(project(":easyarmorstands-assets"))
    jarJar(project(":easyarmorstands-neoforge-api"))
    jarJar(project(":easyarmorstands-modded"))
    jarJar(project(":easyarmorstands-modded-api"))
    jarJar(project(":easyarmorstands-common"))
    jarJar(project(":easyarmorstands-api"))
    jarJar(libs.cloud.neoforge)
    jarJar(libs.cloud.annotations)
    jarJar(libs.cloud.minecraft.extras)
    jarJar(libs.adventure.platform.neoforge)
}

val generateModMetadata by tasks.registering(ProcessResources::class) {
    val props = mapOf(
        "version" to version,
        "neoforgeVersion" to libs.versions.neoforge.get(),
        "minecraftVersion" to libs.versions.minecraft.get()
    )
    inputs.properties(props)
    expand(props)
    from(layout.projectDirectory.dir("src/main/templates"))
    into(layout.buildDirectory.dir("generated/sources/modMetadata"))
}

sourceSets {
    main {
        resources {
            srcDir(generateModMetadata)
        }
    }
}

neoForge.ideSyncTask(generateModMetadata)

tasks {
    jar {
        manifest {
            attributes["Automatic-Module-Name"] = "me.m56738.easyarmorstands.neoforge"
        }
    }
}
