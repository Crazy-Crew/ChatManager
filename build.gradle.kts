import java.awt.Color

plugins {
    alias(libs.plugins.paperweight)
    alias(libs.plugins.shadowJar)
    alias(libs.plugins.runPaper)
    alias(libs.plugins.minotaur)

    `paper-plugin`
}

base {
    archivesName.set(rootProject.name)
}

val buildNumber: String? = System.getenv("BUILD_NUMBER")

rootProject.version = if (buildNumber != null) "${libs.versions.minecraft.get()}-$buildNumber" else "3.13"

val isBeta = true

val content: String = rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8)

val releaseUpdate = Color(27, 217, 106)
val betaUpdate = Color(255, 163, 71)

val color = if (isBeta) betaUpdate else releaseUpdate

dependencies {
    paperweight.paperDevBundle(libs.versions.paper.get())

    implementation(libs.vital.paper)

    implementation(libs.metrics)

    compileOnly(libs.placeholder.api)

    compileOnly(libs.vault) {
        exclude("org.bukkit", "bukkit")
    }

    compileOnly(libs.essentials) {
        exclude("org.spigotmc", "spigot-api")
        exclude("org.bstats", "bstats-bukkit")
    }
}

tasks {
    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }

    assemble {
        dependsOn(reobfJar)

        doLast {
            copy {
                from(reobfJar.get())
                into(rootProject.projectDir.resolve("jars"))
            }
        }
    }

    shadowJar {
        listOf(
            "com.ryderbelserion",
            "org.bstats"
        ).forEach {
            relocate(it, "libs.$it")
        }
    }

    processResources {
        val properties = hashMapOf(
            "name" to rootProject.name,
            "version" to rootProject.version,
            "group" to rootProject.group,
            "description" to rootProject.description,
            "apiVersion" to libs.versions.minecraft.get(),
            "authors" to providers.gradleProperty("authors").get(),
            "website" to providers.gradleProperty("website").get()
        )

        inputs.properties(properties)

        filesMatching("plugin.yml") {
            expand(properties)
        }
    }

    modrinth {
        token.set(System.getenv("MODRINTH_TOKEN"))

        projectId.set(rootProject.name.lowercase())

        versionType.set(if (isBeta) "beta" else "release")

        versionName.set("${rootProject.name} ${rootProject.version}")
        versionNumber.set(rootProject.version as String)

        changelog.set(content)

        uploadFile.set(rootProject.projectDir.resolve("jars/${rootProject.name}-${rootProject.version}.jar"))

        syncBodyFrom.set(rootProject.file("README.md").readText(Charsets.UTF_8))

        gameVersions.set(listOf(libs.versions.minecraft.get()))

        loaders.addAll(listOf("paper", "purpur"))

        autoAddDependsOn.set(false)
        detectLoaders.set(false)
    }

    webhook {
        this.username("Ryder Belserion")

        this.content("<@&888222546573537280>")

        this.embeds {
            this.embed {
                this.color(color)

                this.title("A new version of ChatManager is ready!")

                this.fields {
                    this.field(
                        "Version ${libs.versions.minecraft.get()} build ${rootProject.version}",
                        "Click [here](https://modrinth.com/plugin/${rootProject.name.lowercase()}/version/${rootProject.version}) to download!"
                    )

                    this.field(
                        "Changelog",
                        rootProject.file("DISCORD.md").readText(Charsets.UTF_8)
                    )
                }
            }
        }
    }
}