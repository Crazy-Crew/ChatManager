import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
    id("io.papermc.hangar-publish-plugin")

    id("io.papermc.paperweight.userdev")

    id("xyz.jpenilla.run-paper")

    id("root-plugin")
}

base {
    archivesName.set("${rootProject.name}-${project.name.uppercaseFirstChar()}")
}

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://repo.codemc.io/repository/maven-public/")

    maven("https://repo.triumphteam.dev/snapshots/")

    maven("https://repo.essentialsx.net/releases/")

    maven("https://repo.oraxen.com/releases/")

    flatDir { dirs("libs") }
}

val mcVersion = rootProject.properties["minecraftVersion"] as String
val paperVersion = rootProject.properties["paperVersion"] as String

project.version = if (System.getenv("BUILD_NUMBER") != null) "$paperVersion-${System.getenv("BUILD_NUMBER")}" else paperVersion

dependencies {
    paperweight.paperDevBundle("$mcVersion-R0.1-SNAPSHOT")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        minecraftVersion(mcVersion)
    }

    val directory = File("$rootDir/jars")
    val isBeta: Boolean = rootProject.extra["isBeta"]?.toString()?.toBoolean() ?: false
    val type = if (isBeta) "Beta" else "Release"

    // Publish to hangar.papermc.io.
    hangarPublish {
        publications.register("plugin") {
            version.set("${project.version}")

            id.set(rootProject.name)

            channel.set(type)

            changelog.set(rootProject.file("CHANGELOG.md").readText())

            apiKey.set(System.getenv("hangar_key"))

            platforms {
                /*register(Platforms.PAPER) {
                    jar.set(file("$directory/${rootProject.name}-${project.name.uppercaseFirstChar()}-${project.version}.jar"))

                    platformVersions.set(listOf(mcVersion))
                }*/
            }
        }
    }

    modrinth {
        versionType.set(type.lowercase())

        loaders.addAll("paper", "purpur")
    }
}