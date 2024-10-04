plugins {
    id("java-library")
}

group = "me.m56738"
version = property("version")!!

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://ci.mg-dev.eu/plugin/repository/everything/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.glaremasters.me/repository/bloodshot/") {
        mavenContent {
            includeGroup("com.griefdefender")
        }
    }
    maven("https://ci.athion.net/plugin/repository/tools/") {
        mavenContent {
            includeGroup("com.bekvon.bukkit.residence")
        }
    }
}

java {
    disableAutoTargetJvm()
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
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
