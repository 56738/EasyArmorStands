plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
}

dependencies {
    compileOnlyApi(libs.adventure.api)
    compileOnlyApi(libs.adventure.text.logger.slf4j)
    compileOnlyApi(libs.adventure.text.minimessage)
    compileOnlyApi(libs.adventure.text.serializer.plain)
    compileOnlyApi(libs.gson)
    compileOnlyApi(libs.joml)
    compileOnlyApi(libs.jspecify)
    compileOnlyApi(libs.slf4j.api)
}
