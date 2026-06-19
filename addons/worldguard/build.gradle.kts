plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(project(":"))
    compileOnly(libs.worldguard) {
        exclude("com.google.guava")
        exclude("com.google.code.gson")
    }
}
