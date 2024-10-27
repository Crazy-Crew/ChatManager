plugins {
    `maven-publish`
    `java-library`
}

val buildNumber: String? = System.getenv("BUILD_NUMBER")

rootProject.version = if (buildNumber != null) "${libs.versions.minecraft.get()}-$buildNumber" else "4.0.1"

subprojects.filter { it.name != "api" }.forEach {
    it.project.version = rootProject.version
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "java-library")

    group = "me.h1dd3nxn1nja.chatmanager"
    description = "The kitchen sink of Chat Management!"

    repositories {
        maven("https://repo.codemc.io/repository/maven-public")

        maven("https://repo.crazycrew.us/libraries")
        maven("https://repo.crazycrew.us/releases")

        maven("https://jitpack.io")

        mavenCentral()
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    tasks {
        compileJava {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(21)
        }

        javadoc {
            options.encoding = Charsets.UTF_8.name()
        }

        processResources {
            filteringCharset = Charsets.UTF_8.name()
        }
    }
}