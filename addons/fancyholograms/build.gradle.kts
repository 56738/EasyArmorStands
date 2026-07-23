plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(project(":easyarmorstands-paper"))
    compileOnly(libs.fancyholograms)
}
