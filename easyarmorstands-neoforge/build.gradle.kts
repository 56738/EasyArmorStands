plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.resources")
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
            ideName = "NeoForge Client"
        }
        register("server") {
            server()
            ideName = "NeoForge Server"
        }
    }
}

dependencies {
    implementation(project(":easyarmorstands-modded"))
    implementation(project(":easyarmorstands-neoforge-api"))
    implementation(libs.adventure.platform.neoforge)
    implementation(libs.cloud.neoforge)
    implementation(libs.gizmo.neoforge)
    implementation(libs.neoforge)

    resources(project(":easyarmorstands-assets"))
    jarJar(project(":easyarmorstands-neoforge-api"))
    jarJar(project(":easyarmorstands-modded"))
    jarJar(project(":easyarmorstands-modded-api"))
    jarJar(project(":easyarmorstands-common"))
    jarJar(project(":easyarmorstands-api"))
    jarJar(libs.adventure.platform.neoforge)
    jarJar(libs.cloud.annotations)
    jarJar(libs.cloud.minecraft.extras)
    jarJar(libs.cloud.neoforge)
    jarJar(libs.gizmo.neoforge)
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
            srcDir(tasks.jarJar)
        }
    }
}

neoForge.ideSyncTask(generateModMetadata)

tasks {
    jar {
        manifest {
            attributes["Automatic-Module-Name"] = "me.m56738.easyarmorstands.neoforge"
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    val staticJar by registering(Copy::class) {
        from(jar)
        into(layout.buildDirectory.dir("static"))
        rename { "EasyArmorStands-NeoForge.jar" }
    }

    assemble {
        dependsOn(staticJar)
    }
}
