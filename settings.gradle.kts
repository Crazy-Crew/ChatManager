rootProject.name = "ChatManager"

listOf(
    "version/5.0.0/paper" to "paper",
    "version/5.0.0/core" to "core",

    "version/5.0.0" to "5.0.0",

    "publish" to "publish",

    "paper" to "current",
).forEach(::includeProject)

fun includeProject(pair: Pair<String, String>): Unit = includeProject(pair.first, pair.second)

fun includeProject(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}

fun includeProject(path: String, name: String) {
    includeProject(name) {
        this.name = "${rootProject.name.lowercase()}-$name"
        this.projectDir = File(path)
    }
}

fun includeProject(name: String) {
    includeProject(name) {
        this.name = "${rootProject.name.lowercase()}-$name"
    }
}