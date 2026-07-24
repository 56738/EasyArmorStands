import org.gradle.api.credentials.PasswordCredentials

plugins {
    id("java-library")
    id("maven-publish")
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    repositories {
        maven {
            name = "m56738"
            credentials(PasswordCredentials::class)
            if (project.version.toString().endsWith("-SNAPSHOT")) {
                setUrl("https://repo.56738.me/repository/maven-snapshots/")
            } else {
                setUrl("https://repo.56738.me/repository/maven-releases/")
            }
        }
    }
}
