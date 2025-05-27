plugins {
    alias(libs.plugins.minotaur)
    alias(libs.plugins.feather)
    alias(libs.plugins.hangar)

    `config-java`
}

rootProject.group = "me.h1dd3nxn1nja.chatmanager"

val git = feather.getGit()

val commitHash: String? = git.getCurrentCommitHash().subSequence(0, 7).toString()
val isSnapshot: Boolean = System.getenv("IS_SNAPSHOT") != null
val content: String = if (isSnapshot) "[$commitHash](https://github.com/Crazy-Crew/${rootProject.name}/commit/$commitHash) ${git.getCurrentCommit()}" else rootProject.file("changelog.md").readText(Charsets.UTF_8)

rootProject.version = version()
rootProject.description = "The kitchen sink of Chat Management!"

fun version(): String {
    if (isSnapshot) {
        return "${libs.versions.minecraft.get()}-$commitHash"
    }

    return libs.versions.chatmanager.get()
}

feather {
    rootDirectory = rootProject.rootDir.toPath()

    val data = git.getCurrentCommitAuthorData().copy(author = git.getCurrentCommitAuthorName())

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

allprojects { //todo() why? the gradle shit in buildSrc already applies this...
    apply(plugin = "java-library")
}

tasks {
    withType<Jar> {
        subprojects {
            dependsOn(project.tasks.build)
        }

        // get subproject's built jars
        val jars = subprojects.map { zipTree(it.tasks.jar.get().archiveFile.get().asFile) }

        // merge them into main jar (except their manifests)
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        from(jars) {
            exclude("META-INF/MANIFEST.MF")
        }

        // put behind an action because files don't exist at configuration time
        doFirst {
            // merge all subproject's manifests into main manifest
            jars.forEach { jar ->
                jar.matching { include("META-INF/MANIFEST.MF") }
                    .files.forEach { file ->
                        manifest.from(file)
                    }
            }
        }
    }
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