plugins {
    id("easyarmorstands.base")
}

dependencies {
    implementation(project(":easyarmorstands-plugin"))
    compileOnly(libs.bukkit) {
        isTransitive = false
    }
    compileOnly(libs.towny)
}
