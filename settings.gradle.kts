dependencyResolutionManagement {
    includeBuild("build-logic")
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "ChatManager"

//include("paper", "spigot")
include("spigot")