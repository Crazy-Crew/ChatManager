plugins {
    id("chatmanager.base")

    alias(libs.plugins.runPaper)
    alias(libs.plugins.shadow)
}

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    implementation(project(":core"))

    implementation(libs.fusion.paper)

    //compileOnly(libs.bundles.cloud.paper)

    compileOnly(libs.bundles.shared) {
        exclude("org.spigotmc", "spigot-api")
        exclude("org.bstats", "bstats-bukkit")
        exclude("org.bukkit", "bukkit")
    }

    compileOnly(libs.paper)
}

tasks {
    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }

    assemble {
        dependsOn(shadowJar)

        doLast {
            copy {
                from(shadowJar.get())
                into(rootProject.projectDir.resolve("jars"))
            }
        }
    }

    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")

        /*listOf(
            "com.ryderbelserion.vital",
            "ch.jalu"
        ).forEach {
            relocate(it, "libs.$it")
        }*/
    }

    processResources {
        inputs.properties("name" to rootProject.name)
        inputs.properties("version" to project.version)
        inputs.properties("group" to project.group)
        inputs.properties("apiVersion" to libs.versions.minecraft.get())
        inputs.properties("description" to project.description)
        inputs.properties("website" to "https://modrinth.com/plugin/chatmanager")

        filesMatching("paper-plugin.yml") {
            expand(inputs.properties)
        }
    }
}