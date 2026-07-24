rootProject.name = "easyarmorstands"

pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        mavenCentral()
        gradlePluginPortal()
    }

    includeBuild("build-logic")
}

fun includeAt(name: String, path: String) {
    include(name)
    project(":$name").projectDir = file(path)
}

fun module(name: String) {
    includeAt("easyarmorstands-$name", name)
}

module("api")
module("assets")

fun addon(name: String) {
    includeAt("easyarmorstands-$name", "addons/$name")
}

addon("bentobox")
addon("fancyholograms")
addon("griefdefender")
addon("griefprevention")
addon("headdatabase")
addon("huskclaims")
addon("lands")
addon("plotsquared")
addon("residence")
addon("towny")
addon("traincarts")
addon("worldguard")

includeAt("easyarmorstands-platform", "platform")
includeAt("easyarmorstands-platform-paper", "platform/paper")
includeAt("easyarmorstands-platform-modded", "platform/modded")
includeAt("easyarmorstands-platform-fabric", "platform/fabric")

includeAt("easyarmorstands-paper", "paper")
includeAt("easyarmorstands-paper-api", "paper/api")
includeAt("easyarmorstands-modded", "modded")
includeAt("easyarmorstands-modded-api", "modded/api")
includeAt("easyarmorstands-fabric", "fabric")
includeAt("easyarmorstands-fabric-api", "fabric/api")
