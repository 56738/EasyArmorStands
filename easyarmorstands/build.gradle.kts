plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    api(project(":easyarmorstands-platform"))
    implementation(libs.adventure.text.minimessage)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

tasks {
    test {
        useJUnitPlatform()
    }
}
