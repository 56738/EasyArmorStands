plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
    alias(libs.plugins.moddev)
}

dependencies {
    api(project(":easyarmorstands-platform"))
    compileOnly(libs.adventure.platform.mod.shared)
}

neoForge {
    neoFormVersion = libs.versions.neoform.get()
}
