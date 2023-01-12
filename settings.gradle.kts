dependencyResolutionManagement {
    includeBuild("build-logic")
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    plugins {
        id("com.modrinth.minotaur") version "2.6.0"
        id("com.github.johnrengelman.shadow") version "7.1.2"
        id("xyz.jpenilla.run-paper") version "2.0.0"
    }
}

rootProject.name = "ChatManager"

//include("paper", "spigot")
include("spigot")