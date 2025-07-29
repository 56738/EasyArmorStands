plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
}

dependencies {
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.text.minimessage)
    compileOnly(libs.adventure.text.serializer.plain)
    compileOnly(libs.jetbrains.annotations)
    compileOnly(libs.joml)
    compileOnly(libs.jspecify)
}

tasks {
    jar {
        manifest {
            attributes["FMLModType"] = "LIBRARY"
            attributes["Automatic-Module-Name"] = "me.m56738.easyarmorstands.api"
        }
    }
}
