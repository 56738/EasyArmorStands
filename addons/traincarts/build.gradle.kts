plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(project(":easyarmorstands-paper"))
    compileOnly(libs.paper.api)
    compileOnly(libs.traincarts)
}
