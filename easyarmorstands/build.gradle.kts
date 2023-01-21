plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    api(project(":easyarmorstands-platform"))
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
