plugins {
    `java-library`

    id("com.github.johnrengelman.shadow") version "7.1.2"
}

val serverOne = File("${rootProject.layout.projectDirectory}/server/119")
val serverTwo = File("${rootProject.layout.projectDirectory}/server/118")

//tasks {
    //shadowJar {
        // Create 1.19 server folder.
        //if (!serverOne.exists()) serverOne.mkdirs()

        // Create plugin directory for 1.19 server folder.
        //val serverOnePluginFolder = File(serverOne, "/plugins")

        //if (!serverOnePluginFolder.exists()) serverOnePluginFolder.mkdirs()

        // Create 1.18 server folder.
        //if (!serverTwo.exists()) serverTwo.mkdirs()
        // Create plugin directory for 1.18 server folder.
        //val serverTwoPluginFolder = File(serverTwo, "/plugins")

        //if (!serverTwoPluginFolder.exists()) serverTwoPluginFolder.mkdirs()
    //}
//}