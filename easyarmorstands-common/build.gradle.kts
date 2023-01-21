plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    api(libs.joml)
    api(libs.adventure.api)
    api(libs.cloud.core)
    implementation(libs.adventure.text.minimessage)
    implementation(libs.cloud.annotations)
    implementation(libs.cloud.minecraft.extras)
    annotationProcessor(libs.cloud.annotations)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

tasks {
    test {
        useJUnitPlatform()
    }
}
