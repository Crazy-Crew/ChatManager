plugins {
    id("chat.base")

    alias(libs.plugins.runPaper)
    alias(libs.plugins.shadow)
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")

    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    maven("https://repo.essentialsx.net/releases")
}

dependencies {
    implementation(libs.fusion.paper)

    compileOnly(libs.placeholder.api)

    compileOnly(libs.vault) {
        exclude("org.bukkit", "bukkit")
    }

    compileOnly(libs.essentials) {
        exclude("org.spigotmc", "spigot-api")
        exclude("org.bstats", "bstats-bukkit")
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

        listOf(
            "com.ryderbelserion.fusion"
        ).forEach {
            relocate(it, "libs.$it")
        }
    }

    processResources {
        inputs.properties("name" to rootProject.name)
        inputs.properties("version" to project.version)
        inputs.properties("group" to project.group)
        inputs.properties("apiVersion" to libs.versions.minecraft.get())
        inputs.properties("description" to project.description)
        inputs.properties("website" to "https://modrinth.com/plugin/chatmanager")

        filesMatching("plugin.yml") {
            expand(inputs.properties)
        }
    }
}