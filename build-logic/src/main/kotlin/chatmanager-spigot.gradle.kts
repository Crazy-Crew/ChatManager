plugins {
    `java-library`
}

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
     * NBT API
     */
    maven("https://repo.codemc.org/repository/maven-public/")

    /**
     * EssentialsX Team
     */
    maven("https://repo.essentialsx.net/releases/")

    /**
     * CrazyCrew Team
     */
    maven("https://repo.crazycrew.us/plugins/")

    /**
     * Everything else we need.
     */
    maven("https://jitpack.io/")

    mavenCentral()
}

dependencies {
    implementation("org.bstats", "bstats-bukkit", "3.0.0")

    compileOnly("org.spigotmc", "spigot-api", "1.19.3-R0.1-SNAPSHOT")

    compileOnly("net.essentialsx", "EssentialsX", "2.19.0")

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
}