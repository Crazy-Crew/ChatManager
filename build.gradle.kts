import java.awt.Color

plugins {
    id("chatmanager.root-plugin")
}

val legacyUpdate = Color(255, 73, 110)
val releaseUpdate = Color(27, 217, 106)
val betaUpdate = Color(255, 163, 71)

val isBeta = settings.versions.projectBeta.get().toBoolean()
val projectVersion = settings.versions.projectVersion.get()
val projectName = settings.versions.projectName.get()
val projectExt = settings.versions.projectExtension.get()

val finalVersion = if (isBeta) "$projectVersion+Beta" else projectVersion

val color = if (isBeta) betaUpdate else releaseUpdate
val repo = if (isBeta) "beta" else "releases"

webhook {
    this.avatar("https://cdn.discordapp.com/avatars/209853986646261762/eefe3c03882cbb885d98107857d0b022.png?size=4096")

    this.username("Ryder Belserion")

    this.content("New version of $projectName is ready! <@&929463450214735912>")

    this.embeds {
        this.embed {
            this.color(color)

            this.fields {
                this.field(
                    "Version $finalVersion",
                    "Download Link: https://modrinth.com/$projectExt/${projectName.lowercase()}/version/$finalVersion"
                )

                this.field(
                    "API Update",
                    "Version $finalVersion has been pushed to https://repo.crazycrew.us/#/$repo"
                )
            }

            this.author(
                projectName,
                "https://modrinth.com/$projectExt/${projectName.lowercase()}/versions",
                "https://cdn-raw.modrinth.com/data/IwVOgYiT/c742dee969a8e37393ea6150670c151384ee4ad2.png"
            )
        }
    }
}