plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnlyApi(project(":easyarmorstands-plugin"))
    compileOnly(libs.bukkit)
}
