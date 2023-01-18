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
            minecraftVersion("1.19.3")
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

            //<h3>The first release for ChatManager on Modrinth! 🎉🎉🎉🎉🎉<h3><br> If we want a header.
            changelog.set("""
             <h3>⚠️ Do not use in production unless you are certain there is no issues. ⚠️<h3><br>
                <h2>Changes:</h2>
                 <p>Added the ability to change the hex color format.</p>
                 <p>Note: You cannot use `<>` or () around it, It only supports letters like # or &#'</p>
                 <p>Added a new permission, `chatmanager.mention.receive` which means if the permission is false, They can't get mentioned.</p>
                 <p>Applies to both `@everyone` & `@<username>` | Players also need the permission `chatmanager.mention.receive` to get mention at all.</p>
                <h2>Bug Fixes:</h2>
                 <p>Maybe fixed sounds still playing when toggle mentions is on.</p>
             <a href="https://github.com/orgs/Crazy-Crew/discussions">Send your bug reports/suggestions here</a> 
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