plugins {
    id("chatmanager-spigot")

    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "${rootProject.group}.ChatManager"
description = "The kitchen sink of Chat Management."

val buildNumber: String? = System.getenv("BUILD_NUMBER")
val buildVersion = "${project.version}-b$buildNumber-SNAPSHOT"

tasks {
    shadowJar {
        if (buildNumber != null) {
            archiveFileName.set("${rootProject.name}-${buildVersion}.jar")
        } else {
            archiveFileName.set("${rootProject.name}-${project.version}.jar")
        }

        listOf(
            "org.bstats"
        ).forEach {
            relocate(it, "${rootProject.group}.plugin.lib.$it")
        }
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(
                "name" to rootProject.name,
                "group" to project.group,
                "version" to if (buildNumber != null) buildVersion else project.version,
                "description" to project.description
            )
        }
    }
}