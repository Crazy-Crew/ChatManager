plugins {
    id("chatmanager-spigot")

    id("com.modrinth.minotaur") version "2.6.0"

    id("com.github.johnrengelman.shadow") version "7.1.2"

    `maven-publish`
}

val isBeta = true

fun getPluginVersion(): String {
    return if (isBeta) "${project.version}-BETA" else project.version.toString()
}

fun getPluginVersionType(): String {
    return if (isBeta) "beta" else "release"
}

tasks {
    shadowJar {
        archiveFileName.set("${rootProject.name}-${getPluginVersion()}.jar")

        listOf(
            "org.bstats"
        ).forEach {
            relocate(it, "${rootProject.group}.plugin.lib.$it")
        }
    }

    modrinth {
        token.set(System.getenv("MODRINTH_TOKEN"))
        projectId.set("chatmanager")

        versionName.set("${rootProject.name} ${getPluginVersion()}")
        versionNumber.set(getPluginVersion())

        versionType.set(getPluginVersionType())

        uploadFile.set(shadowJar.get())

        autoAddDependsOn.set(true)

        gameVersions.addAll(listOf("1.8", "1.9", "1.10", "1.11", "1.12", "1.13", "1.14", "1.15", "1.16", "1.17", "1.18", "1.19"))
        loaders.addAll(listOf("spigot", "paper", "purpur"))

        //<h3>The first release for CrazyCrates on Modrinth! 🎉🎉🎉🎉🎉<h3><br> If we want a header.
        changelog.set("""
             <h3>The first release for ChatManager on Modrinth! 🎉🎉🎉🎉🎉<h3><br>
                <h2>Changes:</h2>
                 <p>N/A</p>
                <h2>Bug Fixes:</h2>
                 <p>N/A</p>
            """.trimIndent())
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(
                "name" to rootProject.name,
                "group" to project.group,
                "version" to getPluginVersion(),
                "description" to project.description,
                "website" to "https://modrinth.com/plugin/chatmanager"
            )
        }
    }
}

publishing {
    val mavenExt: String = if (isBeta) "beta" else "releases"

    repositories {
        maven("https://repo.crazycrew.us/$mavenExt") {
            name = "crazycrew"
            //credentials(PasswordCredentials::class)
            credentials {
                username = System.getenv("REPOSITORY_USERNAME")
                password = System.getenv("REPOSITORY_PASSWORD")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = "${project.group}"
            artifactId = rootProject.name.toLowerCase()
            version = getPluginVersion()
            from(components["java"])
        }
    }
}