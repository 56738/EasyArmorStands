plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
}

dependencies {
    api(project(":easyarmorstands-api"))
    api(project(":easyarmorstands-platform-paper"))
    compileOnly(libs.paper.api)
}
