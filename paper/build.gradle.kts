plugins {
    `paper-plugin`
}

project.group = "${rootProject.group}"

repositories {
    maven("https://repo.essentialsx.net/releases/")
}

dependencies {
    implementation(project(":common"))

    implementation(libs.bundles.cloud.paper)
    implementation(libs.fusion.paper)
    implementation(libs.metrics)

    compileOnly(libs.placeholderapi) {
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
        archiveBaseName.set("${rootProject.name}-${rootProject.version}")

        copy {
            from(project.layout.buildDirectory.dir("libs"))
            into(rootProject.layout.buildDirectory.dir("libs"))
        }

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
            "description" to rootProject.description.toString(),
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
        jvmArgs("-Dcom.mojang.eula.agree=true")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }
}