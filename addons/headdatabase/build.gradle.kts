plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(project(":easyarmorstands-plugin"))
    compileOnly(libs.paper.api)
    compileOnly(libs.headdatabase.api)
}
