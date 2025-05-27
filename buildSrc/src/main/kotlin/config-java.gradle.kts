plugins {
    id("com.gradleup.shadow")

    `java-library`
}

project.version = rootProject.version

repositories {
    maven("https://repo.codemc.io/repository/maven-public")

    maven("https://repo.triumphteam.dev/snapshots")

    maven("https://repo.crazycrew.us/libraries")
    maven("https://repo.crazycrew.us/releases")

    maven("https://jitpack.io")

    mavenCentral()
}

dependencies {

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        archiveVersion.set("")
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()

        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}