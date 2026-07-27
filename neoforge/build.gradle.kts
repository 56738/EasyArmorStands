import xyz.jpenilla.resourcefactory.neoforge.DependencyOrdering

plugins {
    id("easyarmorstands.base")
    alias(libs.plugins.moddev)
    alias(libs.plugins.resource.factory.neoforge.convention)
}

dependencies {
    api(project(":easyarmorstands-neoforge-api"))
    api(project(":easyarmorstands-modded"))
    api(libs.cloud.neoforge)
    api(libs.adventure.platform.neoforge)
    api(libs.gizmo.neoforge)

    accessTransformers(project(":easyarmorstands-platform-modded"))

    jarJar(libs.adventure.platform.neoforge)
    jarJar(libs.cloud.annotations)
    jarJar(libs.cloud.minecraft.extras)
    jarJar(libs.cloud.neoforge)
    jarJar(libs.configurate.core)
    jarJar(libs.configurate.yaml)
    jarJar(libs.gizmo.neoforge)
    jarJar(project(":"))
    jarJar(project(":easyarmorstands-api"))
    jarJar(project(":easyarmorstands-modded"))
    jarJar(project(":easyarmorstands-modded-api"))
    jarJar(project(":easyarmorstands-neoforge-api"))
    jarJar(project(":easyarmorstands-platform"))
    jarJar(project(":easyarmorstands-platform-modded"))
    jarJar(project(":easyarmorstands-platform-neoforge"))
}

neoForge {
    version = libs.versions.neoforge.get()

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

    mods {
        register("easyarmorstands") {
            sourceSet(sourceSets.main.get())
        }
    }
}

tasks {
    processResources {
        from("../src/main/resources/assets/easyarmorstands/icon.png")
        from("../platform/modded/src/main/resources/META-INF/accesstransformer.cfg") {
            into("META-INF")
        }
    }

    jar {
        archiveBaseName = "EasyArmorStands-NeoForge"
        destinationDirectory.set(layout.buildDirectory)
    }

    val staticJar = register<Copy>("staticJar") {
        group = "build"
        description = "Creates a jar archive with a static file name."
        from(jar)
        into(layout.buildDirectory.dir("static"))
        rename { "EasyArmorStands-NeoForge.jar" }
    }

    assemble {
        dependsOn(staticJar)
    }
}

neoForgeModsToml {
    license = "GPL-3.0-or-later"
    mod("easyarmorstands") {
        version = project.version.toString()
        displayName = "EasyArmorStands"
        displayUrl = "https://modrinth.com/mod/easyarmorstands"
        logoFile = "icon.png"
        logoBlur = false
        authors = "56738"
        description = "Armor stand and display entity editor"
        dependencies {
            required("cloud", "[${libs.versions.cloud.minecraft.modded.get()},)") {
                ordering = DependencyOrdering.AFTER
            }
            required("gizmo", "[${libs.versions.gizmo.modded.get()},)") {
                ordering = DependencyOrdering.AFTER
            }
            required("neoforge", "[${libs.versions.neoforge.get()},)")
            required("minecraft", "[${libs.versions.minecraft.get()},)")
        }
    }
}
