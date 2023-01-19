plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    api(project(":easyarmorstands-platform"))
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

tasks {
    test {
        useJUnitPlatform()
    }
}
