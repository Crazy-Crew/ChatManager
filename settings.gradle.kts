dependencyResolutionManagement {
    versionCatalogs {
        create("settings") {
            from(files("gradle/settings.versions.toml"))
        }
    }

    repositories.gradlePluginPortal()
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "ChatManager"

val lowerCase = rootProject.name.lowercase()

listOf("paper").forEach(::includeProject)

fun includeProject(name: String) {
    include(name) {
        this.name = "$lowerCase-$name"
    }
}

fun includeModule(name: String) {
    include(name) {
        this.name = "$lowerCase-module-$name"
        this.projectDir = file("modules/$name")
    }
}

fun includeType(name: String) {
    include(name) {
        this.name = "$lowerCase-$name"
        this.projectDir = file("minecraft/$name")
    }
}

fun include(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}