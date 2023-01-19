plugins {
    id("easyarmorstands.base-conventions")
}

dependencies {
    api(project(":easyarmorstands-platform"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
