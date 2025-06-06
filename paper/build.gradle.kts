plugins {
    `config-paper`
}

project.description = "Paper version of ChatManager!"
project.group = "${rootProject.group}.paper"

dependencies {
    implementation(project(":chatmanager-core"))

    compileOnly(libs.fusion.paper)
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    runPaper.folia.registerTask()

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }
}