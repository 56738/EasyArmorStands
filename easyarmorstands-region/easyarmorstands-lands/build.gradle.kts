plugins {
    id("easyarmorstands.base")
}

dependencies {
    implementation(project(":easyarmorstands-region"))
    compileOnly(libs.bukkit)
    compileOnly(libs.lands.api)
}
