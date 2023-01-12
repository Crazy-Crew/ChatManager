plugins {

    id("com.github.johnrengelman.shadow")

    id("xyz.jpenilla.run-paper")

    id("com.modrinth.minotaur")

    id("chatmanager.spigot-plugin")

    `maven-publish`
}

releaseBuild {

    tasks {
        shadowJar {
            archiveFileName.set("${getProjectName()}+${getProjectVersion()}.jar")

            listOf(
                "org.bstats"
            ).forEach { value ->
                relocate(value, "${getProjectGroup()}.plugin.library.$value")
            }
        }

        runServer {
            minecraftVersion("1.8.8")
        }

        modrinth {
            token.set(System.getenv("MODRINTH_TOKEN"))
            projectId.set(getProjectName().toLowerCase())

            versionName.set("${getProjectName()} ${getProjectVersion()}")
            versionNumber.set(getProjectVersion())

            versionType.set(getProjectType())

            uploadFile.set(shadowJar.get())

            autoAddDependsOn.set(true)

            dependencies {
                optional.project("essentialsx")
            }

            gameVersions.addAll(listOf("1.8", "1.9", "1.10", "1.11", "1.12", "1.13", "1.14", "1.15", "1.16", "1.17", "1.18", "1.19"))
            loaders.addAll(listOf("spigot", "paper", "purpur"))

            //<h3>The first release for CrazyCrates on Modrinth! 🎉🎉🎉🎉🎉<h3><br> If we want a header.
            changelog.set("""
             <h3>The first release for ChatManager on Modrinth! 🎉🎉🎉🎉🎉<h3><br>
                <h2>Changes:</h2>
                 <p>Changed warning label about permissions plugin to severe.</p>
                 <p>Disable auto broadcast by default so it doesn't spam you on first install</p>
                <h2>Bug Fixes:</h2>
                 <p>Fixed a few bugs due to Essentials not being invoked correctly./p>
                 <p>Fixed a few bugs due to Vault not being invoked correctly.</p>
                 <p>Fixed a bug where tasks or listeners will still try to run if Vault not found</p>
                 <p>Fixed a bug where messages/prefixes weren't being colored or replaced.</p>
                 <p>Fixed a bug where messages weren't showing up in /reply or /msg</p>
                 <p>Fixed a bug where it would say Cannot use this command when doing /chatmanager reload</p>
            """.trimIndent())
        }

        processResources {
            filesMatching("plugin.yml") {
                expand(
                    "name" to getProjectName(),
                    "group" to getProjectGroup(),
                    "version" to getProjectVersion(),
                    "description" to getProjectDescription(),
                    "website" to "https://modrinth.com/${getExtension()}/${getProjectName().toLowerCase()}"
                )
            }
        }
    }

    publishing {
        repositories {
            maven("https://repo.crazycrew.us/beta") {
                name = "crazycrew"
                // Used for locally publishing.
                //credentials(PasswordCredentials::class)

                credentials {
                    username = System.getenv("REPOSITORY_USERNAME")
                    password = System.getenv("REPOSITORY_PASSWORD")
                }
            }
        }

        publications {
            create<MavenPublication>("maven") {
                groupId = getProjectGroup()
                artifactId = "${getProjectName().toLowerCase()}-spigot"
                version = getProjectVersion()
                from(components["java"])
            }
        }
    }
}