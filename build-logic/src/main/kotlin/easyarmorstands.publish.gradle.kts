plugins {
    id("easyarmorstands.publish.base")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
