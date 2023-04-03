plugins {
    id("chatmanager.root-plugin")

    id("io.papermc.paperweight.userdev")
}

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://repo.essentialsx.net/releases/")
}

dependencies {
    paperweight.paperDevBundle("1.19.4-R0.1-SNAPSHOT")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    reobfJar {
        val file = File("$rootDir/jars")

        if (!file.exists()) file.mkdirs()

        outputJar.set(layout.buildDirectory.file("$file/${rootProject.name}-Paper-${rootProject.version}.jar"))
    }
}