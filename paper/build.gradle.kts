plugins {
    `config-paper`
}

dependencies {
    implementation(project(":chatmanager-core"))

    implementation(libs.fusion.paper)
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        listOf(
            "com.ryderbelserion.fusion"
        ).forEach {
            relocate(it, "libs.$it")
        }
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()

        inputs.properties(
            "name" to rootProject.name,
            "version" to rootProject.version,
            "description" to rootProject.description,
            "minecraft" to libs.versions.minecraft.get(),
            "group" to project.group
        )

        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        with(copySpec {
            from("src/main/resources/plugin.yml") {
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