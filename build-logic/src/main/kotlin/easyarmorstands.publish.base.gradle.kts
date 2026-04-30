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
        maven("https://ci.mg-dev.eu/plugin/repository/everything") {
            name = "MGDev"
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
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
