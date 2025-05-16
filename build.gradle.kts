plugins {
    id("com.ryderbelserion.feather.core") version "0.3.1"

    alias(libs.plugins.minotaur)

    id("root-plugin")
}

rootProject.group = "me.h1dd3nxn1nja.chatmanager"

val git = feather.getGit()

val commitHash: String? = git.getCurrentCommitId().subSequence(0, 7).toString()
val username: String = git.getCommitAuthorName()
val isSnapshot: Boolean = true
val content: String = if (isSnapshot) "[$commitHash](https://github.com/Crazy-Crew/${rootProject.name}/commit/$commitHash) ${git.getLatestCommitMessage()}" else rootProject.file("changelog.md").readText(Charsets.UTF_8)

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
    discord {
        webhook {
            group("chatmanager")
            task("dev-build")

            if (System.getenv("BUILD_WEBHOOK") != null) {
                this.post(System.getenv("BUILD_WEBHOOK"))
            }

            this.username(username)

            this.avatar(git.getGithubInformation().avatar)

            this.embeds {
                this.embed {
                    this.color("#ffa347")

                    this.title("A new dev version of ${rootProject.name} is ready!")

                    this.fields {
                        this.field(
                            "Version ${rootProject.version}",
                            "Click [here](https://modrinth.com/plugin/${rootProject.name.lowercase()}/version/${rootProject.version}) to download!"
                        )

                        this.field(
                            "Changelog",
                            content
                        )
                    }
                }
            }
        }

        webhook {
            group("chatmanager")
            task("release-build")

            if (System.getenv("BUILD_WEBHOOK") != null) {
                this.post(System.getenv("BUILD_WEBHOOK"))
            }

            this.username(username)

            this.avatar(git.getGithubInformation().avatar)

            this.content("<@&1372358375433834537>")

            this.embeds {
                this.embed {
                    this.color("#1bd96a")

                    this.title("A new release version of ${rootProject.name} is ready!")

                    this.fields {
                        this.field(
                            "Version ${rootProject.version}",
                            "Click [here](https://modrinth.com/plugin/${rootProject.name.lowercase()}/version/${rootProject.version}) to download!"
                        )

                        this.field(
                            "Changelog",
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