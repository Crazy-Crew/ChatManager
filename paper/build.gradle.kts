plugins {
    alias(libs.plugins.runPaper)
    alias(libs.plugins.shadow)

    id("paper-plugin")
}

project.group = "me.h1dd3nxn1nja.chatmanager"
project.description = "The kitchen sink of Chat Management!"

val buildNumber: String? = System.getenv("BUILD_NUMBER")
val isPublishing: String? = System.getenv("IS_PUBLISHING")

project.version = if (buildNumber != null && isPublishing == null) "${libs.versions.minecraft.get()}-$buildNumber" else "4.0.4"

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    maven("https://repo.essentialsx.net/releases/")
}

dependencies {
    implementation(libs.bundles.cloud.paper)

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

    compileOnly(libs.configurate.yaml)
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
            "org.incendo",
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
        inputs.properties("name" to rootProject.name)
        inputs.properties("version" to project.version)
        inputs.properties("group" to project.group)
        inputs.properties("apiVersion" to libs.versions.minecraft.get())
        inputs.properties("description" to project.description)
        inputs.properties("website" to rootProject.properties["website"].toString())

        filesMatching("paper-plugin.yml") {
            expand(inputs.properties)
        }
    }

    runPaper.folia.registerTask()

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }
}
