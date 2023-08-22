plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(project(":easyarmorstands-region"))
    compileOnly(libs.bukkit)
    compileOnly(libs.worldguard.v6)
}
