plugins {
    id("java")
}

group = "me.m56738"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
}

dependencies {
    compileOnly(project(":easyarmorstands-module"))
    compileOnly(project(":easyarmorstands-api"))
    compileOnly("org.bukkit:bukkit:1.9.4-R0.1-SNAPSHOT") {
        isTransitive = false
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
