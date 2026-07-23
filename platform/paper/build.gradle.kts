plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
}

dependencies {
    api(project(":easyarmorstands-platform"))
    compileOnly(libs.paper.api)
}
