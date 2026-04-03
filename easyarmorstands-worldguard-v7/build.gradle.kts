plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(project(":easyarmorstands-plugin"))
    compileOnly(libs.worldguard.v7)
}
