plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
    alias(libs.plugins.moddev)
}

dependencies {
    api(project(":easyarmorstands-api"))
    api(project(":easyarmorstands-platform-modded"))
}

neoForge {
    neoFormVersion = libs.versions.neoform.get()
}
