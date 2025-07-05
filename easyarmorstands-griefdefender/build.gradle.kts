plugins {
    id("easyarmorstands.base")
}

dependencies {
    implementation(project(":easyarmorstands-plugin"))
    compileOnly(libs.bukkit)
    compileOnly(libs.griefdefender.api)
}
