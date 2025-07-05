plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(libs.paper.api)
    implementation(project(":easyarmorstands-plugin"))
    compileOnly(libs.worldguard.v7)
}
