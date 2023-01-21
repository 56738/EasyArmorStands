plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    implementation(project(":easyarmorstands-bukkit"))
    compileOnly(libs.bukkit) {
        isTransitive = false
    }
    compileOnly(libs.headdatabase.api)
}
