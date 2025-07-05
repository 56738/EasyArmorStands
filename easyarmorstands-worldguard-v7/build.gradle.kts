plugins {
    id("easyarmorstands.base")
}

dependencies {
    implementation(project(":easyarmorstands-plugin"))
    compileOnly("org.bukkit:bukkit:1.13-R0.1-SNAPSHOT")
    compileOnly(libs.worldguard.v7)
}
