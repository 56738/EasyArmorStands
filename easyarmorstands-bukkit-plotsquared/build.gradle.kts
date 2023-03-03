plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    implementation(project(":easyarmorstands-bukkit"))
    compileOnly(libs.bukkit) {
        isTransitive = false
    }
    compileOnly(libs.headdatabase.api)
    implementation(platform("com.intellectualsites.bom:bom-1.18.x:1.22"))
    compileOnly("com.plotsquared:PlotSquared-Core")
    compileOnly("com.plotsquared:PlotSquared-Bukkit") {
        isTransitive = false
    }
}
