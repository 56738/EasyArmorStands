rootProject.name = "EasyArmorStands"

pluginManagement {
    includeBuild("build-logic")
    repositories {
        maven("https://maven.fabricmc.net")
        mavenCentral()
        gradlePluginPortal()
    }
}

include("easyarmorstands-api")
include("easyarmorstands-assets")
include("easyarmorstands-bentobox")
include("easyarmorstands-common")
include("easyarmorstands-fabric")
include("easyarmorstands-fabric-api")
include("easyarmorstands-fabric-repack")
include("easyarmorstands-fancyholograms")
include("easyarmorstands-griefdefender")
include("easyarmorstands-griefprevention")
include("easyarmorstands-headdatabase")
include("easyarmorstands-huskclaims")
include("easyarmorstands-lands")
include("easyarmorstands-modded")
include("easyarmorstands-modded-api")
include("easyarmorstands-neoforge")
include("easyarmorstands-neoforge-api")
include("easyarmorstands-paper")
include("easyarmorstands-paper-api")
include("easyarmorstands-plotsquared")
include("easyarmorstands-plugin")
include("easyarmorstands-residence")
include("easyarmorstands-towny")
include("easyarmorstands-traincarts")
include("easyarmorstands-worldguard")
