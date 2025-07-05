plugins {
    id("easyarmorstands.base")
}

dependencies {
    implementation(project(":easyarmorstands-plugin"))
    compileOnly(libs.paper.api)
    compileOnly(libs.bentobox)
}
