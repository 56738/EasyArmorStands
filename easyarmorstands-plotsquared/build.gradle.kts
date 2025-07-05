plugins {
    id("easyarmorstands.base")
}

dependencies {
    implementation(project(":easyarmorstands-plugin"))
    compileOnly(libs.bukkit)
    compileOnly(libs.plotsquared.core) {
        exclude("net.kyori", "adventure-api")
    }
    compileOnly(libs.plotsquared.bukkit)
}
