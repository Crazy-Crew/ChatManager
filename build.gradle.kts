import com.ryderbelserion.feather.tools.latestCommitHistory
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

val nextNumber: String? = if (System.getenv("NEXT_BUILD_NUMBER") != null) System.getenv("NEXT_BUILD_NUMBER") else "SNAPSHOT"

rootProject.version = "${libs.versions.minecraft.get()}-$nextNumber"

val isSnapshot = true

val content: String = rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8)

val releaseUpdate = Color(27, 217, 106)
val betaUpdate = Color(255, 163, 71)

val color = if (isSnapshot) betaUpdate else releaseUpdate

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
            "apiVersion" to "1.20.6",
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

        versionType.set("beta")

        versionName.set("${rootProject.name} ${rootProject.version}")
        versionNumber.set(rootProject.version as String)

        changelog.set(content)

        uploadFile.set(rootProject.projectDir.resolve("jars/${rootProject.name}-${rootProject.version}.jar"))

        syncBodyFrom.set(rootProject.file("README.md").readText(Charsets.UTF_8))

        gameVersions.set(listOf("1.20.6", "1.21"))

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
                        "Version ${libs.versions.minecraft.get()} build ${System.getenv("NEXT_BUILD_NUMBER")}",
                        "Click [here](https://modrinth.com/plugin/${rootProject.name.lowercase()}/version/${libs.versions.minecraft.get()}-${System.getenv("NEXT_BUILD_NUMBER")}) to download!"
                    )
                }
            }
        }
    }
}