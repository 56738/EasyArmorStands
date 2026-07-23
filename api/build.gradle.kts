plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
}

dependencies {
    compileOnlyApi(project(":easyarmorstands-platform"))
}
