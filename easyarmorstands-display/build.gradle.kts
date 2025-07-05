plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(libs.paper.api)
    implementation(project(":easyarmorstands-display-api"))
    compileOnly(project(":easyarmorstands-plugin"))
    compileOnly(libs.brigadier)
}
