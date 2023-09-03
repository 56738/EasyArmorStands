plugins {
    id("easyarmorstands.base")
}

dependencies {
    implementation(project(":easyarmorstands-region"))
    compileOnly(libs.bukkit)
    implementation(platform("com.intellectualsites.bom:bom-1.18.x:1.30"))
    compileOnly("com.plotsquared:PlotSquared-Core") {
        exclude("net.kyori", "adventure-api")
    }
    compileOnly("com.plotsquared:PlotSquared-Bukkit") {
        isTransitive = false
    }
}

tasks {
    withType<JavaCompile>().configureEach {
        options.release.set(17)
    }
}
