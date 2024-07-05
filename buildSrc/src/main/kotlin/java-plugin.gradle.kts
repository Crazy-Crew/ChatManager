import task.ReleaseWebhook
import task.WebhookExtension

plugins {
    `maven-publish`

    `java-library`
}

repositories {
    maven("https://repo.codemc.io/repository/maven-public")

    maven("https://repo.crazycrew.us/snapshots")

    maven("https://repo.crazycrew.us/releases")

    maven("https://jitpack.io")

    flatDir { dirs("libs") }

    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks {
    // Creating the extension to be available on the root gradle
    val webhookExtension = extensions.create("webhook", WebhookExtension::class)

    // Register the task
    register<ReleaseWebhook>("webhook") {
        extension = webhookExtension
    }

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