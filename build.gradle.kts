plugins {
    id("com.ryderbelserion.feather.core") version "0.3.2"

    alias(libs.plugins.minotaur)

    id("root-plugin")
}

rootProject.group = "me.h1dd3nxn1nja.chatmanager"

val git = feather.getGit()

val commitHash: String? = git.getCurrentCommitHash().subSequence(0, 7).toString()
val isSnapshot: Boolean = true
val content: String = if (isSnapshot) "[$commitHash](https://github.com/Crazy-Crew/${rootProject.name}/commit/$commitHash) ${git.getCurrentCommit()}" else rootProject.file("changelog.md").readText(Charsets.UTF_8)

rootProject.version = if (isSnapshot) "${libs.versions.minecraft.get()}-$commitHash" else libs.versions.chatmanager.get()
rootProject.description = "The kitchen sink of Chat Management!"

val mergedJar by configurations.creating<Configuration> {
    isCanBeResolved = true
    isCanBeConsumed = false
    isVisible = false
}

dependencies {
    mergedJar(project(":paper"))
}

feather {
    rootDirectory = rootProject.rootDir.toPath()

    val data = git.getCurrentCommitAuthorData()

    discord {
        webhook {
            group(rootProject.name.lowercase())
            task("dev-build")

            if (System.getenv("BUILD_WEBHOOK") != null) {
                post(System.getenv("BUILD_WEBHOOK"))
            }

            username(data.author)

            avatar(data.avatar)

            embeds {
                embed {
                    color("#ffa347")

                    title("A new dev version of ${rootProject.name} is ready!")

                    fields {
                        field(
                            "Version ${rootProject.version}",
                            "Click [here](https://modrinth.com/plugin/${rootProject.name.lowercase()}/version/${rootProject.version}) to download!"
                        )

                        field(
                            ":bug: Report Bugs",
                            "https://github.com/Crazy-Crew/${rootProject.name}/issues"
                        )

                        field(
                            ":hammer: Changelog",
                            content
                        )
                    }
                }
            }
        }

        webhook {
            group(rootProject.name.lowercase())
            task("release-build")

            if (System.getenv("BUILD_WEBHOOK") != null) {
                post(System.getenv("BUILD_WEBHOOK"))
            }

            username(data.author)

            avatar(data.avatar)

            content("<@&1372358375433834537>")

            embeds {
                embed {
                    color("#1bd96a")

                    title("A new release version of ${rootProject.name} is ready!")

                    fields {
                        field(
                            "Version ${rootProject.version}",
                            "Click [here](https://modrinth.com/plugin/${rootProject.name.lowercase()}/version/${rootProject.version}) to download!"
                        )

                        field(
                            ":bug: Report Bugs",
                            "https://github.com/Crazy-Crew/${rootProject.name}/issues"
                        )

                        field(
                            ":hammer: Changelog",
                            content
                        )
                    }
                }
            }
        }
    }
}

tasks.withType<Jar> {
    dependsOn(mergedJar)

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    val jars = mergedJar.map { zipTree(it) }

    from(jars)
}

modrinth {
    token = System.getenv("MODRINTH_TOKEN")

    projectId = rootProject.name

    versionName = "${rootProject.version}"
    versionNumber = "${rootProject.version}"
    versionType = if (isSnapshot) "beta" else "release"

    changelog = content

    gameVersions.addAll(listOf(libs.versions.minecraft.get()))

    uploadFile = tasks.jar.get().archiveFile.get()

    loaders.addAll(listOf("paper", "folia", "purpur"))

    syncBodyFrom = rootProject.file("description.md").readText(Charsets.UTF_8)

    autoAddDependsOn = false
    detectLoaders = false
}