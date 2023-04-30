plugins {
    id("paper-plugin")
    id("library-plugin")

    id("xyz.jpenilla.run-paper") version "2.0.1"
}

dependencies {
    implementation(libs.crazycore)

    compileOnly(libs.bstats.bukkit)

    compileOnly(libs.config.me)

    compileOnly(libs.aikars)

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