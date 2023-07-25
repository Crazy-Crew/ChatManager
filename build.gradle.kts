plugins {
    id("root-plugin")
}

defaultTasks("build")

rootProject.group = "me.h1dd3nxn1nja.chatmanager"
rootProject.description = "The kitchen sink of Chat Management."
rootProject.version = "3.9.3"

val combine by tasks.registering(Jar::class) {
    dependsOn("build")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(files(subprojects.map {
        it.layout.buildDirectory.file("libs/${rootProject.name}-${it.name}-${it.version}.jar").get()
    }).filter { it.name != "MANIFEST.MF" }.map { if (it.isDirectory) it else zipTree(it) })
}

allprojects {
    listOf(
        ":paper"
    ).forEach {
        project(it) {
            apply(plugin = "java")

            if (this.name == "paper") {
                dependencies {
                    implementation("org.bstats", "bstats-bukkit", "3.0.2")

                    compileOnly("me.clip", "placeholderapi", "2.11.3")

                    compileOnly("com.github.MilkBowl", "VaultAPI", "1.7.1") {
                        exclude("org.bukkit", "bukkit")
                    }

                    compileOnly("net.essentialsx", "EssentialsX", "2.19.0")
                }
            }
        }
    }
}

tasks {
    assemble {
        subprojects.forEach {
            dependsOn(":${it.project.name}:build")
        }

        finalizedBy(combine)
    }
}