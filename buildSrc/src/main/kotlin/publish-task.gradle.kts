import java.io.ByteArrayOutputStream
import java.io.File

plugins {
    id("root-plugin")

    id("featherpatcher")
    id("com.modrinth.minotaur")
}

val isSnapshot = rootProject.version.toString().contains("snapshot")
val type = if (isSnapshot) "beta" else "release"

// The commit id for the "main" branch prior to merging a pull request.
val start = "cbea2cc"

// The commit id BEFORE merging the pull request so before "Merge pull request #30"
val end = "5ffdca7"

val commitLog = getGitHistory().joinToString(separator = "") { formatGitLog(it) }

val desc = """
## Changes:
 * Added 1.20-1.20.1 support.

## API:
 * N/A

## Bugs:
 * Submit any bugs @ https://github.com/Crazy-Crew/${rootProject.name}/issues 

## Commits
            
<details>
          
<summary>Other</summary>

commitLog
            
</details>

""".trimIndent()

val versions = listOf(
    "1.20.1",
    "1.20"
)

fun getGitHistory(): List<String> {
    val output: String = ByteArrayOutputStream().use { outputStream ->
        project.exec {
            executable("git")
            args("log",  "$start..$end", "--format=format:%h %s")
            standardOutput = outputStream
        }

        outputStream.toString()
    }

    return output.split("\n")
}

fun formatGitLog(commitLog: String): String {
    val hash = commitLog.take(7)
    val message = commitLog.substring(8) // Get message after commit hash + space between
    return "[$hash](https://github.com/Crazy-Crew/${rootProject.name}/commit/$hash) $message<br>"
}

val javaComponent: SoftwareComponent = components["java"]

tasks {
    modrinth {
        token.set(System.getenv("MODRINTH_TOKEN"))
        projectId.set(rootProject.name.lowercase())

        versionName.set("${rootProject.name} ${rootProject.version}")
        versionNumber.set(rootProject.version.toString())

        versionType.set(type)

        val file = File("$rootDir/jars")
        if (!file.exists()) file.mkdirs()

        uploadFile.set(layout.buildDirectory.file("$file/${rootProject.name}-${rootProject.version}.jar"))

        autoAddDependsOn.set(true)

        gameVersions.addAll(versions)

        loaders.addAll(listOf("paper", "purpur"))

        changelog.set(desc)
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = rootProject.group.toString()
                artifactId = "${rootProject.name.lowercase()}-api"
                version = rootProject.version.toString()

                from(javaComponent)
            }
        }

        repositories {
            maven {
                credentials {
                    this.username = System.getenv("gradle_username")
                    this.password = System.getenv("gradle_password")
                }

                if (isSnapshot) {
                    url = uri("https://repo.crazycrew.us/snapshots/")
                    return@maven
                }

                url = uri("https://repo.crazycrew.us/releases/")
            }
        }
    }
}