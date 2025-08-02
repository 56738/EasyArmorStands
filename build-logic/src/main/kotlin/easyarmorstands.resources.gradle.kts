plugins {
    id("java")
}

val resources by configurations.registering {
    isTransitive = false
}

tasks {
    processResources {
        dependsOn(resources)
        from(resources.map { c -> c.map { zipTree(it) } })
    }
}
