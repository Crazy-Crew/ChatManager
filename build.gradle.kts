plugins {
    id("root-plugin")
}

defaultTasks("build")

rootProject.group = "me.h1dd3nxn1nja.chatmanager"
rootProject.description = "The kitchen sink of Chat Management."
rootProject.version = "3.10.2"

tasks {
    assemble {
        val jarsDir = File("$rootDir/jars")
        if (jarsDir.exists()) jarsDir.delete()

        subprojects.forEach { project ->
            dependsOn(":${project.name}:build")

            doLast {
                if (!jarsDir.exists()) jarsDir.mkdirs()

                val file = file("${project.layout.buildDirectory.get()}/libs/${rootProject.name}-${rootProject.version}.jar")

                copy {
                    from(file)
                    into(jarsDir)
                }
            }
        }
    }
}