plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(project(":easyarmorstands-plugin"))
    compileOnly(libs.bukkit)
    compileOnly(libs.lands.api)
}
