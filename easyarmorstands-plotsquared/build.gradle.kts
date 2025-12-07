plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(project(":easyarmorstands-plugin"))
    compileOnly(libs.paper.api)
    compileOnly(libs.plotsquared.core) {
        exclude("net.kyori", "adventure-api")
    }
    compileOnly(libs.plotsquared.bukkit)
}
