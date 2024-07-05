import com.ryderbelserion.feather.tools.formatLog
import com.ryderbelserion.feather.tools.latestCommitHash
import com.ryderbelserion.feather.tools.latestCommitMessage

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

val isSnapshot = false

val content: String = if (isSnapshot) {
    formatLog(latestCommitHash(), latestCommitMessage(), rootProject.name, "Crazy-Crew")
} else {
    rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8)
}

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
}