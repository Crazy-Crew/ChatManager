plugins {
    id("root-plugin")

    id("com.modrinth.minotaur") version "2.8.2"
}

defaultTasks("build")

rootProject.group = "me.h1dd3nxn1nja.chatmanager"
rootProject.description = "The kitchen sink of Chat Management."
rootProject.version = "3.10"

val combine by tasks.registering(Jar::class) {
    dependsOn("build")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(files(subprojects.map {
        it.layout.buildDirectory.file("libs/${rootProject.name}-${it.name}-${it.version}.jar").get()
    }).filter { it.name != "MANIFEST.MF" }.map { if (it.isDirectory) it else zipTree(it) })
}

tasks {
    assemble {
        subprojects.forEach {
            dependsOn(":${it.project.name}:build")
        }

        finalizedBy(combine)
    }
}

val description = """
## New Features:
 * Changed the mention sound format for all places with sounds. I've added an example of what it looks like below.
 ```yml
#=================================================================================================#
# When a player mentions another players name.
#=================================================================================================#
Mentions:

  Enable: true
  
  # The sound that's played to the receiver
  # https://jd.papermc.io/paper/1.20/org/bukkit/Sound.html
  sound:
    toggle: false
    value: 'ENTITY_PLAYER_LEVELUP'
    volume: '1.0'
    pitch: '1.0'
  
  # The symbol players have to use to tag other players, Leave it blank for nothing.
  Tag_Symbol: "@"
  
  # The color the players name will be highlighted as when mentioned in chat.
  # To disable make it Mention_Color: ""
  Mention_Color: ""

  # The title message that's sent to the player mentioned.
  Title:
    Enable: true
    Header: "&cMentioned"
    Footer: "&7You have been mentioned by {player}"
```
 * Changed the mention format for Private Messages.
``` 
#=================================================================================================#
# Private messages allow you to secretly message another player without everyone else seeing.
#=================================================================================================#
Private_Messages:

  Sender:

    Format: "&c&l(!) &f&l[&e&lYou &d-> &e{receiver}&f&l] &b"

  Receiver:

    Format: "&c&l(!) &f&l[&e{player} &d-> &e&lYou&f&l] &b"

  # The sound that's played to the receiver
  # https://jd.papermc.io/paper/1.20/org/bukkit/Sound.html
  sound:
    toggle: false
    value: 'ENTITY_PLAYER_LEVELUP'
    volume: '1.0'
    pitch: '1.0'
```
## Fix:
 * Fixed /msg not using the sound
 * Fixed @mention not working in-game
 * Fixed the staff chat boss bar being shown even if disabled.
   * also allowed staff chat bar to be reloaded if the player is in staffchat.
 * Fixed block commands not being disabled.
 * Fixed /ping command
    
## Other:
 * [Feature Requests](https://github.com/Crazy-Crew/${rootProject.name}/discussions/categories/features)
 * [Bug Reports](https://github.com/Crazy-Crew/${rootProject.name}/issues)
""".trimIndent()

val versions = listOf(
    "1.20",
    "1.20.1"
)

val isSnapshot = rootProject.version.toString().contains("snapshot")
val type = if (isSnapshot) "beta" else "release"

modrinth {
    autoAddDependsOn.set(false)

    token.set(System.getenv("MODRINTH_TOKEN"))

    projectId.set(rootProject.name.lowercase())

    versionName.set("${rootProject.name} ${rootProject.version}")
    versionNumber.set("${rootProject.version}")

    uploadFile.set(combine.get())

    gameVersions.addAll(versions)

    changelog.set(description)

    loaders.addAll("paper", "purpur")
}