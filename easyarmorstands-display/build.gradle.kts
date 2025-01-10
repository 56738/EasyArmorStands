plugins {
    id("easyarmorstands.base")
}

dependencies {
    implementation(project(":easyarmorstands-display-api"))
    compileOnly(project(":easyarmorstands-plugin"))
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT") {
        isTransitive = false
    }
    compileOnly(libs.brigadier)
}

tasks {
    withType<JavaCompile>().configureEach {
        options.release.set(17)
    }
}
