plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(project(":easyarmorstands-plugin"))
    compileOnly(project(":easyarmorstands-display"))
    compileOnly(project(":easyarmorstands-display-api"))
    compileOnly(libs.fancyholograms)
}
