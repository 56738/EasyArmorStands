plugins {
    id("easyarmorstands.base")
}

dependencies {
    implementation(project(":easyarmorstands-region"))
    compileOnly("org.bukkit:bukkit:1.13-R0.1-SNAPSHOT")
    compileOnly(libs.worldguard.v7)
}

tasks {
    withType<JavaCompile>().configureEach {
        options.release.set(17)
    }
}
