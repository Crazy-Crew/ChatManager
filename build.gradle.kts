plugins {
    alias(libs.plugins.indra.git)
    alias(libs.plugins.minotaur)

    id("root-plugin")
}

rootProject.group = "me.h1dd3nxn1nja.chatmanager"

val commitHash: String? = indraGit.commit()?.name()
val isSnapshot: Boolean = true

rootProject.version = if (isSnapshot) "${libs.versions.minecraft.get()}-${commitHash?.subSequence(0, 7)}" else libs.versions.chatmanager.get()
rootProject.description = "The kitchen sink of Chat Management!"

val mergedJar by configurations.creating<Configuration> {
    isCanBeResolved = true
    isCanBeConsumed = false
    isVisible = false
}

dependencies {
    mergedJar(project(":paper"))
}

tasks.withType<Jar> {
    dependsOn(mergedJar)

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    val jars = mergedJar.map { zipTree(it) }

    from(jars)
}

modrinth {
    token = System.getenv("MODRINTH_TOKEN")

    projectId = "SimpleEdit"

    versionName = "${rootProject.version}"
    versionNumber = "${rootProject.version}"
    versionType = if (isSnapshot) "beta" else "release"

    changelog = if (isSnapshot) System.getenv("COMMIT_MESSAGE") else rootProject.file("changelog.md").readText(Charsets.UTF_8)

    gameVersions.addAll(listOf(libs.versions.minecraft.get()))

    uploadFile = tasks.jar.get().archiveFile.get()

    loaders.addAll(listOf("paper", "folia", "purpur"))

    syncBodyFrom = rootProject.file("description.md").readText(Charsets.UTF_8)

    autoAddDependsOn = false
    detectLoaders = false
}