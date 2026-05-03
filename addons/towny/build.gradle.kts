plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(project(":"))
    compileOnly(libs.paper.api)
    compileOnly(libs.towny)
}
