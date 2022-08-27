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
    implementation(project(":easyarmorstands-math"))
    compileOnly("org.bukkit:bukkit:1.8.8-R0.1-SNAPSHOT") {
        isTransitive = false
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
