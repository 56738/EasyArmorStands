plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    api(libs.joml)
    api(libs.adventure.api)
    api(libs.cloud.core)
    api(libs.cloud.annotations)
    api(libs.cloud.minecraft.extras)
    api(libs.adventure.text.minimessage)
    api(libs.adventure.text.serializer.legacy)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

tasks {
    test {
        useJUnitPlatform()
    }
}
