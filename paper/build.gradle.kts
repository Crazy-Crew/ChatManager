plugins {
    alias(libs.plugins.runPaper)
    alias(libs.plugins.shadow)

    id("paper-plugin")
}

project.group = "${rootProject.group}"
project.version = rootProject.version
project.description = "The kitchen sink of Chat Management!"

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
    assemble {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")

        listOf(
            "com.ryderbelserion.fusion",
            "org.bstats"
        ).forEach {
            relocate(it, "libs.$it")
        }

        doLast {
            copy {
                from(shadowJar.get())
                into(rootProject.projectDir.resolve("jars"))
            }
        }
    }

    processResources {
        filesMatching("plugin.yml") {
            expand("name" to rootProject.name,
                "description" to rootProject.description,
                "minecraft" to libs.versions.minecraft.get(),
                "version" to rootProject.version,
                "group" to project.group)
        }
    }

    runPaper.folia.registerTask()

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }
}
