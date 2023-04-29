plugins {
    id("paper-plugin")
    id("library-plugin")

    id("xyz.jpenilla.run-paper") version "2.0.1"
}

dependencies {
    implementation(libs.bstats.bukkit)

    implementation(libs.triumph.cmds)

    implementation(libs.config.me)

    compileOnly(libs.placeholder.api)
    compileOnly(libs.vault.api)

    compileOnly(libs.essentialsx)
}

tasks {
    reobfJar {
        val file = File("$rootDir/jars")

        if (!file.exists()) file.mkdirs()

        outputJar.set(layout.buildDirectory.file("$file/${rootProject.name}-${rootProject.version}.jar"))
    }

    runServer {
        minecraftVersion("1.19.4")
    }
}