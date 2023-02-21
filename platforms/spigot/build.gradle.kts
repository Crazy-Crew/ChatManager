@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("chatmanager.spigot-plugin")

    alias(settings.plugins.minotaur)
}

repositories {
    /**
     * PAPI Team
     */
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    /**
     * EngineHub Team
     */
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    implementation(libs.bstats.bukkit)

    compileOnly(libs.spigotmc)

    compileOnly(libs.essentialsx)

    compileOnly(libs.placeholder.api)

    compileOnly(libs.vault.api)
}

val projectDescription = settings.versions.projectDescription.get()
val projectGithub = settings.versions.projectGithub.get()
val projectGroup = settings.versions.projectGroup.get()
val projectName = settings.versions.projectName.get()
val projectExt = settings.versions.projectExtension.get()

val isBeta = settings.versions.projectBeta.get().toBoolean()

val projectVersion = settings.versions.projectVersion.get()

val finalVersion = if (isBeta) "$projectVersion+Beta" else projectVersion

val projectNameLowerCase = projectName.toLowerCase()

val repo = if (isBeta) "beta" else "releases"
val type = if (isBeta) "beta" else "release"

tasks {
    shadowJar {
        archiveFileName.set("${projectName}+$finalVersion.jar")

        listOf(
            "org.bstats"
        ).forEach { relocate(it, "$projectGroup.plugin.library.$it") }
    }

    modrinth {
        token.set(System.getenv("MODRINTH_TOKEN"))
        projectId.set(projectNameLowerCase)

        versionName.set("$projectName $finalVersion")
        versionNumber.set(finalVersion)

        versionType.set(type)

        uploadFile.set(shadowJar.get())

        autoAddDependsOn.set(true)

        dependencies {
            optional.project("essentialsx")
        }

        gameVersions.addAll(listOf("1.8", "1.9", "1.10", "1.11", "1.12", "1.13", "1.14", "1.15", "1.16", "1.17", "1.18", "1.19"))
        loaders.addAll(listOf("spigot", "paper", "purpur"))

        //<h3>The first release for ChatManager on Modrinth! 🎉🎉🎉🎉🎉<h3><br> If we want a header.
        changelog.set("""
             <h3>⚠️ Do not use in production unless you are certain there is no issues because I am not certain there is no issues. ⚠️<h3><br>
                <h2>Changes:</h2>
                 <p>Hex color format is now configurable.</p>
                 <p>Added a new permission that requires players to have `chatmanager.mention` & the receivers ( the ones being pinged ) to have `chatmanager.mention.receive`</p>
                <h2>Under the hood changes</h2>
                 <p>Re-organized the build script for the last time.</p>
                 <p>Cleaned up a few pieces of code.</p>
                <h2>Bug Fixes:</h2>
                 <p>Fixed potentially sounds playing twice, I could not reproduce it./p>
                 <p>Fixed /rules command not working.</p>
            """.trimIndent())
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(
                "name" to projectName,
                "group" to projectGroup,
                "version" to finalVersion,
                "description" to projectDescription,
                "website" to "https://modrinth.com/$projectExt/$projectNameLowerCase"
            )
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = projectGroup
            artifactId = "$projectNameLowerCase-paper"
            version = finalVersion

            from(components["java"])

            pom {
                name.set(projectName)

                description.set(projectDescription)
                url.set(projectGithub)

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://www.opensource.org/licenses/mit-license.php")
                    }
                }

                developers {
                    developer {
                        id.set("ryderbelserion")
                        name.set("Ryder Belserion")
                    }

                    developer {
                        id.set("H1DD3NxN1NJA")
                        name.set("H1DD3NxN1NJA")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/Crazy-Crew/$projectName.git")
                    developerConnection.set("scm:git:ssh://github.com/Crazy-Crew/$projectName.git")
                    url.set(projectGithub)
                }
            }
        }
    }

    repositories {
        maven("https://repo.crazycrew.us/$repo") {
            name = "crazycrew"
            // Used for locally publishing.
            // credentials(PasswordCredentials::class)

            credentials {
                username = System.getenv("REPOSITORY_USERNAME")
                password = System.getenv("REPOSITORY_PASSWORD")
            }
        }
    }
}