rootProject.name = "ChatManager"

pluginManagement {
    repositories {
        maven("https://repo.crazycrew.us/releases")

        gradlePluginPortal()
    }
}

plugins {
    id("com.ryderbelserion.feather-settings") version "0.0.1"
}