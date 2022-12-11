plugins {
    id("chatmanager-spigot")

    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.h1dd3nxn1nja.chatmanager"
version = "3.9.0"
description = "The kitchen sink of Chat Management."

val buildNumber: String? = System.getenv("BUILD_NUMBER")

val jenkinsVersion = "${project.version}-b$buildNumber"

val jarFolder = File("${rootProject.layout.projectDirectory}/jars")

tasks {
    shadowJar {
        if (buildNumber != null) {
            archiveFileName.set("${rootProject.name}-[v${jenkinsVersion}]-Paper.jar")
        } else {
            archiveFileName.set("${rootProject.name}-[v${project.version}]-Paper.jar")
        }

        listOf(
            "org.bstats"
        ).forEach {
            relocate(it, "${rootProject.group}.plugin.lib.$it")
        }

        // Create universal build folder.
        if (!jarFolder.exists()) jarFolder.mkdirs()

        doLast {
            copy {
                from("build/libs/${rootProject.name}-[v${project.version}]-Paper.jar")
                into(rootProject.layout.projectDirectory.dir("jars"))
            }
        }
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(
                "name" to rootProject.name,
                "group" to project.group,
                "version" to project.version,
                "description" to project.description
            )
        }
    }
}