plugins {
    alias(libs.plugins.userdev) apply false

    `java-library`
}

rootProject.group = "me.h1dd3nxn1nja.chatmanager"
rootProject.description = "The kitchen sink of Chat Management!"
rootProject.version = if (System.getenv("BUILD_NUMBER") != null) "3.11-${System.getenv("BUILD_NUMBER")}" else "3.11"

subprojects {
    apply(plugin = "io.papermc.paperweight.userdev")
    apply(plugin = "java-library")

    repositories {
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

        maven("https://repo.papermc.io/repository/maven-public/")

        maven("https://repo.codemc.io/repository/maven-public/")

        maven("https://repo.triumphteam.dev/snapshots/")

        maven("https://repo.essentialsx.net/releases/")

        maven("https://repo.crazycrew.us/snapshots/")

        maven("https://repo.crazycrew.us/releases/")

        maven("https://repo.oraxen.com/releases/")

        maven("https://jitpack.io/")

        flatDir { dirs("libs") }

        mavenCentral()
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of("17"))
    }

    tasks {
        compileJava {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(17)
        }

        javadoc {
            options.encoding = Charsets.UTF_8.name()
        }

        processResources {
            filteringCharset = Charsets.UTF_8.name()
        }
    }
}

tasks {
    assemble {
        doFirst {
            delete("$rootDir/jars")
        }
    }
}