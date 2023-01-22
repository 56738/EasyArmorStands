plugins {
    id("easyarmorstands.base-conventions")
}

repositories {
    maven("https://ci.mg-dev.eu/plugin/repository/everything/") {
        content {
            includeGroupByRegex("com\\.bergerkiller(\\..*)?")
        }
    }
}

dependencies {
    implementation(project(":easyarmorstands-bukkit"))
    compileOnly(libs.bukkit) {
        isTransitive = false
    }
    compileOnly(libs.bkcommonlib)
    compileOnly(libs.traincarts)
}
