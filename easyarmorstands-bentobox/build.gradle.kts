plugins {
    id("easyarmorstands.base")
}

repositories {
    maven("https://repo.codemc.io/repository/bentoboxworld/")
}

dependencies {
    compileOnly(project(":easyarmorstands-plugin"))
    compileOnly(libs.bukkit)
    compileOnly(libs.bentobox)
}
