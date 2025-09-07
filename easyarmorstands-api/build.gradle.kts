plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
}

dependencies {
    compileOnly(libs.bukkit)
    compileOnly(libs.jetbrains.annotations)
    compileOnlyApi(project(":easyarmorstands-api-dependencies", configuration = "shadow"))
}
