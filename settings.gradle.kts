rootProject.name = "EasyArmorStands"

pluginManagement {
    includeBuild("build-logic")
}

include("easyarmorstands-plugin")

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
