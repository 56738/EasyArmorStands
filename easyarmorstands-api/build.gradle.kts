plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
}

dependencies {
    compileOnly(libs.paper.api)
}

tasks {
    jar {
        manifest {
            attributes["FMLModType"] = "LIBRARY"
            attributes["Automatic-Module-Name"] = "me.m56738.easyarmorstands.api"
        }
    }
}
