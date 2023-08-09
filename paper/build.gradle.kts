plugins {
    id("paper-plugin")
}

group = "${rootProject.group}.paper"

base {
    archivesName.set("${rootProject.name}-${project.name}")
}

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://repo.essentialsx.net/releases/")

    maven("https://jitpack.io/")
}

dependencies {
    implementation("org.bstats", "bstats-bukkit", "3.0.2")

    compileOnly("me.clip", "placeholderapi", "2.11.3")

    compileOnly("com.github.MilkBowl", "VaultAPI", "1.7.1") {
        exclude("org.bukkit", "bukkit")
    }

    compileOnly("net.essentialsx", "EssentialsX", "2.19.0")

    compileOnly("com.github.LeonMangler", "SuperVanish", "6.2.6-4")
}

val component: SoftwareComponent = components["java"]

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = rootProject.group.toString()
                artifactId = "${rootProject.name.lowercase()}-${project.name.lowercase()}-api"
                version = rootProject.version.toString()

                from(component)
            }
        }
    }

    assemble {
        dependsOn(shadowJar)
    }

    reobfJar {
        outputJar.set(file("$buildDir/libs/${rootProject.name}-${project.name}-${project.version}.jar"))
    }

    shadowJar {
        archiveBaseName.set("${rootProject.name}-${project.name}")
        archiveClassifier.set("")
        mergeServiceFiles()

        listOf(
            "org.bstats",
        ).forEach {
            relocate(it, "libs.$it")
        }
    }

    processResources {
        val props = mapOf(
                "name" to rootProject.name,
                "group" to project.group,
                "version" to rootProject.version,
                "description" to rootProject.description,
                "authors" to rootProject.properties["authors"],
                "apiVersion" to "1.20",
                "website" to "https://modrinth.com/plugin/${rootProject.name.lowercase()}"
        )

        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}