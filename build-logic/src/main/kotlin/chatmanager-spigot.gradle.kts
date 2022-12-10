plugins {
    `java-library`
}

rootProject.group = "me.h1dd3nxn1nja.chatmanager"
rootProject.version = "4.8.0"
rootProject.description = "The kitchen sink of Chat Management."

repositories {
    /**
     * PAPI Team
     */
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    /**
     * Spigot Team
     */
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")

    /**
     * CrazyCrew Team
     */
    maven("https://repo.crazycrew.us/plugins/")
}

dependencies {
    implementation("org.bstats", "bstats-bukkit", "3.0.0")

    compileOnly("org.spigotmc", "spigot-api", "1.19.3-R0.1-SNAPSHOT")

    compileOnly("me.clip", "placeholderapi", "2.11.2") {
        exclude(group = "org.spigotmc", module = "spigot")
        exclude(group = "org.bukkit", module = "bukkit")
    }

    compileOnly("com.github.MilkBowl", "VaultAPI", "1.7")
}

tasks {
    compileJava {
        targetCompatibility = "8"
        sourceCompatibility = "8"
    }

    processResources {
       filesMatching("plugin.yml") {
            expand(
               "name" to rootProject.name,
               "group" to rootProject.group,
                "version" to rootProject.version,
                "description" to rootProject.description
            )
        }
    }
}