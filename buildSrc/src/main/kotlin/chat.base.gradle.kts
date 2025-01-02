plugins {
    id("chat.parent")

    `maven-publish`
}

group = "me.h1dd3nxn1nja.chatmanager"
description = "The kitchen sink of Chat Management!"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://repo.codemc.io/repository/maven-public/")

    maven("https://repo.crazycrew.us/libraries/")
    maven("https://repo.crazycrew.us/releases/")

    maven("https://jitpack.io/")

    mavenCentral()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))

    withSourcesJar()
    withJavadocJar()
}

tasks {
    publishing {
        repositories {
            maven {
                url = uri("https://repo.crazycrew.us/releases/")

                credentials {
                    this.username = System.getenv("gradle_username")
                    this.password = System.getenv("gradle_password")
                }
            }
        }
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.compilerArgs.add("-parameters")
        options.release.set(21)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()

        options.quiet()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}