plugins {
    id("java-library")
}

group = "me.m56738"
version = property("version")!!

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://ci.mg-dev.eu/plugin/repository/everything/")
}

tasks {
    withType<JavaCompile>().configureEach {
        options.release.set(8)
        options.encoding = "UTF-8"
    }

    javadoc {
        options.encoding = "UTF-8"
        isFailOnError = true
        (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:all,-missing", "-quiet")
    }

    withType<AbstractArchiveTask>().configureEach {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
}
