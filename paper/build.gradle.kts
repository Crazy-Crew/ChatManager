plugins {
    `config-paper`
}

project.group = "${rootProject.group}"

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    maven("https://repo.essentialsx.net/releases/")
}

dependencies {
    implementation(libs.fusion.paper)

    implementation(libs.metrics)

    compileOnly(libs.placeholder.api) {
        exclude("org.bstats", "bstats-bukkit")
    }

    compileOnly(libs.vault) {
        exclude("org.bukkit", "bukkit")
    }

    compileOnly(libs.essentials) {
        exclude("org.spigotmc", "spigot-api")
        exclude("org.bstats", "bstats-bukkit")
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        listOf(
            "com.ryderbelserion.fusion",
            "org.bstats"
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