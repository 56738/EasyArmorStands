plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(project(":easyarmorstands-plugin"))
    compileOnly(libs.bukkit) {
        isTransitive = false
    }
    compileOnly(libs.traincarts)
}
