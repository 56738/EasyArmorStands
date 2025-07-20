plugins {
    id("java-library")
}

val bundle = configurations.create("bundle") {
    isTransitive = false
}

tasks {
    jar {
        dependsOn(bundle)
        from(provider { bundle.map { zipTree(it) } }) {
            exclude("META-INF/MANIFEST.MF")
        }
    }
}
