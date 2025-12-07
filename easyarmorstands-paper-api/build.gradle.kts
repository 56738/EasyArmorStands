plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnlyApi(project(":easyarmorstands-api"))
}
