plugins {
    id("easyarmorstands.base")
}

dependencies {
    implementation(project(":easyarmorstands-display-api"))
    compileOnly(project(":easyarmorstands-plugin"))
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly(libs.brigadier)
}
