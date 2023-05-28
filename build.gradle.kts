plugins {
    id("paper-plugin")
    id("library-plugin")

    id("xyz.jpenilla.run-paper") version "2.0.1"
}

dependencies {
    api(project(":chatmanager-api"))

    implementation(libs.bstats.bukkit)

    implementation(libs.config.me)

    implementation(libs.aikars)

    implementation(libs.jorel)

    implementation(libs.stick)
}

tasks {
    reobfJar {
        val file = File("$rootDir/jars")

        if (!file.exists()) file.mkdirs()

        outputJar.set(layout.buildDirectory.file("$file/${rootProject.name}-${rootProject.version}.jar"))
    }

    shadowJar {
        exclude("**/META-INF/**")

        listOf(
            "com.ryderbelserion.stick",
            "dev.jorel.commandapi",
            "org.bstats",
            "co.aikar",
            "ch.jalu"
        ).forEach { pack -> relocate(pack, "${rootProject.group}.$pack") }
    }

    runServer {
        minecraftVersion("1.19.4")
    }
}