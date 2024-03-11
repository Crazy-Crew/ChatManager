pluginManagement {
    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")

        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")

        gradlePluginPortal()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            version("adventure4", "4.16.0")
            version("cluster", "6.3")

            library("triumphcmds", "dev.triumphteam", "triumph-cmd-bukkit").version("2.0.0-SNAPSHOT")

            library("cluster_paper", "com.ryderbelserion.cluster", "paper").versionRef("cluster")
            library("cluster_api", "com.ryderbelserion.cluster", "paper").versionRef("cluster")

            library("adventure4", "net.kyori", "adventure-text-minimessage").versionRef("adventure4")
            library("minimessage4", "net.kyori", "adventure-api").versionRef("adventure4")

            library("placeholderapi", "me.clip", "placeholderapi").version("2.11.5")
            library("vault", "com.github.MilkBowl", "VaultAPI").version("1.7.1")
            library("metrics", "org.bstats", "bstats-bukkit").version("3.0.2")
            library("configme", "ch.jalu", "configme").version("1.4.1")

            bundle("adventure", listOf("adventure4", "minimessage4"))
        }
    }
}

rootProject.name = "ChatManager"

include("paper")
include("common")