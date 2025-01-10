plugins {
    id("easyarmorstands.base")
}

dependencies {
    implementation(project(":easyarmorstands-plugin"))
    compileOnly(libs.bukkit) {
        isTransitive = false
    }
    implementation(platform("com.intellectualsites.bom:bom-1.18.x:1.31"))
    compileOnly("com.plotsquared:PlotSquared-Core") {
        exclude("net.kyori", "adventure-api")
    }
    compileOnly("com.plotsquared:PlotSquared-Bukkit") {
        isTransitive = false
    }
}
