import java.awt.Color

plugins {
    id("chatmanager.root-plugin")
}

val legacyUpdate = Color(255, 73, 110)
val releaseUpdate = Color(27, 217, 106)
val snapshotUpdate = Color(255, 163, 71)

val commitMessage: String? = System.getenv("COMMIT_MESSAGE")

releaseBuild {
    val pluginVersion = getProjectVersion()
    val pluginName = getProjectName()

    val versionColor = if (isBeta()) snapshotUpdate else releaseUpdate

    val pageExtension = getExtension()

    webhook {
        this.avatar("https://cdn.discordapp.com/avatars/209853986646261762/eefe3c03882cbb885d98107857d0b022.png")

        this.username("Ryder Belserion")

        this.content("New version of $pluginName is ready! <@&888222546573537280>")

        // this.content("New version of $pluginName is ready!")

        this.embeds {
            this.embed {
                this.color(versionColor)

                this.fields {
                    this.field(
                        "Version $pluginVersion",
                        "Download Link: https://modrinth.com/$pageExtension/${pluginName.toLowerCase()}/version/$pluginVersion"
                    )

                    val urlExt = if (isBeta()) "beta" else "releases"

                    this.field(
                        "API Update",
                        "Version $pluginVersion has been pushed to https://repo.crazycrew.us/#/$urlExt/"
                    )
                }

                this.author(
                    pluginName,
                    "https://modrinth.com/$pageExtension/${pluginName.toLowerCase()}/versions",
                    "https://cdn-raw.modrinth.com/data/IwVOgYiT/c742dee969a8e37393ea6150670c151384ee4ad2.png"
                )
            }
        }
    }
}