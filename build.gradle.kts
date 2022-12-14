import java.awt.Color

plugins {
    id("chatmanager.root-plugin")
}

val legacyUpdate = Color(255,73,110)
val releaseUpdate = Color(27,217,106)
val snapshotUpdate = Color(255,163,71)

val commitMessage: String? = System.getenv("COMMIT_MESSAGE")
val isBeta: Boolean = extra["isBeta"].toString().toBoolean()

webhook {
    this.avatar("https://cdn.discordapp.com/avatars/209853986646261762/eefe3c03882cbb885d98107857d0b022.png?size=4096")

    this.username("Ryder Belserion")

    //this.content("New version of ${project.name} is ready! <@888222546573537280>")

    this.content("New version of ${project.name} is ready!")

    this.embeds {
        this.embed {
            if (isBeta) this.color(snapshotUpdate) else this.color(releaseUpdate)

            this.fields {
                this.field(
                    "Version ${project.version}",
                    "Download Link: https://modrinth.com/plugin/${project.name.toLowerCase()}/version/${project.version}"
                )

                if (isBeta) {
                    if (commitMessage != null) this.field("Commit Message", commitMessage)

                    this.field("Beta", "They will be hosted on the same page labeled as `Beta`")

                    this.field(
                        "API Update",
                        "Version ${project.version} has been pushed to https://repo.crazycrew.us/#/beta/"
                    )
                }

                if (!isBeta) this.field("API Update","Version ${project.version} has been pushed to https://repo.crazycrew.us/#/releases/")
            }

            this.author(
                project.name,
                "https://modrinth.com/plugin/${project.name.toLowerCase()}/versions",
                "https://cdn-raw.modrinth.com/data/IwVOgYiT/c742dee969a8e37393ea6150670c151384ee4ad2.png"
            )
        }
    }
}