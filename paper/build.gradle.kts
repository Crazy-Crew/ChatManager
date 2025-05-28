plugins {
    `config-paper`
}

project.description = "Paper version of ChatManager!"
project.group = "${rootProject.group}.paper"

dependencies {
    implementation(project(":chatmanager-core"))

    implementation(libs.triumph.cmds)

    implementation(libs.fusion.paper)
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()

        inputs.properties(
            "name" to rootProject.name,
            "version" to rootProject.version,
            "description" to project.description,
            "minecraft" to libs.versions.minecraft.get(),
            "group" to project.group
        )

        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        with(copySpec {
            from("src/main/resources/paper-plugin.yml") {
                expand(inputs.properties)
            }
        })
    }

    runPaper.folia.registerTask()

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }
}