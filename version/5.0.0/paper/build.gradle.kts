plugins {
    alias(libs.plugins.runPaper)
    alias(libs.plugins.shadow)

    id("paper-plugin")
}

project.description = "Paper based version of ChatManager"
project.group = "${rootProject.group}.paper"

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    implementation(project(":chatmanagermercuriomc-core"))

    implementation(libs.fusion.paper)

    compileOnly(libs.bundles.cloud.paper)
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveBaseName.set(project.name + "-" + rootProject.version)
        archiveClassifier.set("")

        listOf(
            "com.ryderbelserion.fusion"
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
        inputs.properties("version" to rootProject.version)
        inputs.properties("group" to project.group)
        inputs.properties("apiVersion" to libs.versions.minecraft.get())
        inputs.properties("description" to project.description)
        inputs.properties("website" to rootProject.properties["website"].toString())

        filesMatching("paper-plugin.yml") {
            expand(inputs.properties)
        }
    }

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }
}