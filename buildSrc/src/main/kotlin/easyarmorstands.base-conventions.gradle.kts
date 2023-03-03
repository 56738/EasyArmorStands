plugins {
    id("java-library")
}

group = "me.m56738"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

tasks {
    withType<JavaCompile> {
        options.release.set(8)
        options.encoding = "UTF-8"
    }

    withType<AbstractArchiveTask> {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
}

java {
    disableAutoTargetJvm()
}
