plugins {
    id("easyarmorstands.base")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnlyApi(libs.checker.qual)
    compileOnlyApi(libs.apiguardian.api)
    api(libs.cloud.annotations)
    api(project(":easyarmorstands-assets"))
    api(project(":easyarmorstands-paper"))
    runtimeOnly(project(":easyarmorstands-bentobox"))
    runtimeOnly(project(":easyarmorstands-fancyholograms"))
    runtimeOnly(project(":easyarmorstands-griefdefender"))
    runtimeOnly(project(":easyarmorstands-griefprevention"))
    runtimeOnly(project(":easyarmorstands-headdatabase"))
    runtimeOnly(project(":easyarmorstands-huskclaims"))
    runtimeOnly(project(":easyarmorstands-lands"))
    runtimeOnly(project(":easyarmorstands-plotsquared"))
    runtimeOnly(project(":easyarmorstands-residence"))
    runtimeOnly(project(":easyarmorstands-towny"))
    runtimeOnly(project(":easyarmorstands-traincarts"))
    runtimeOnly(project(":easyarmorstands-worldguard"))
}
