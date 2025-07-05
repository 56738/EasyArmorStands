plugins {
    id("java-library")
}

repositories {
    mavenCentral()
    maven("https://repo.56738.me")
    maven("https://maven.enginehub.org/repo/") {
        mavenContent {
            includeGroupByRegex("com\\.sk89q\\..*")
        }
    }
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://ci.mg-dev.eu/plugin/repository/everything/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.glaremasters.me/repository/bloodshot/") {
        mavenContent {
            includeGroup("com.griefdefender")
        }
    }
    maven("https://repo.glaremasters.me/repository/towny/") {
        mavenContent {
            includeGroup("com.palmergames.bukkit.towny")
        }
    }
    maven("https://ci.athion.net/plugin/repository/tools/") {
        mavenContent {
            includeGroup("com.bekvon.bukkit.residence")
        }
    }
    maven("https://repo.fancyplugins.de/releases/") {
        mavenContent {
            includeModule("de.oliver", "FancyHolograms")
        }
    }
    maven("https://repo.william278.net/releases") {
        mavenContent {
            includeGroup("net.william278.huskclaims")
        }
    }
    maven("https://repo.codemc.io/repository/maven-public/") {
        mavenContent {
            includeGroup("de.tr7zw")
            includeGroup("world.bentobox")
        }
    }
    maven("https://jitpack.io") {
        mavenContent {
            includeGroup("com.github.angeschossen")
            includeGroup("com.github.GriefPrevention")
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks {
    withType<JavaCompile>().configureEach {
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
