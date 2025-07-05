plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
}

dependencies {
    compileOnly(libs.paper.api)
    api(project(":easyarmorstands-api"))
}
