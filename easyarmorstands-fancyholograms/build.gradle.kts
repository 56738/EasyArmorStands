plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(project(":easyarmorstands-plugin"))
    compileOnly(project(":easyarmorstands-display"))
    compileOnly(project(":easyarmorstands-display-api"))
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly(libs.fancyholograms)
}
