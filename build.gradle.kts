import task.WebhookExtension
import java.awt.Color

plugins {
    id("chatmanager.root-plugin")

    id("featherpatcher") version "0.0.0.1"
}

val releaseUpdate = Color(27, 217, 106)
val betaUpdate = Color(255, 163, 71)
val changeLogs = Color(37, 137, 204)

val beta = settings.versions.beta.get().toBoolean()
val extension = settings.versions.extension.get()

val color = if (beta) betaUpdate else releaseUpdate
val repo = if (beta) "beta" else "releases"

val url = "https://modrinth.com/$extension/${rootProject.name.lowercase()}/versions"
val download = "https://modrinth.com/$extension/${rootProject.name.lowercase()}/version/${rootProject.version}"
val msg = if (beta) "New version of ${rootProject.name} is ready!" else "New version of ${rootProject.name} is ready! <@&929463441159254066>"

rootProject.version = "3.9.1.1"

webhook {
    this.avatar("https://en.gravatar.com/avatar/${WebhookExtension.Gravatar().md5Hex("no-reply@ryderbelserion.com")}.jpeg")

    this.username("Ryder Belserion")

    this.content(msg)

    this.embeds {
        this.embed {
            this.color(color)

            this.fields {
                this.field(
                    "Download: ",
                    url
                )

                this.field(
                    "API: ",
                    "https://repo.crazycrew.us/#/$repo/${rootProject.group.toString().replace(".", "/")}/${rootProject.name.lowercase()}-api/${rootProject.version}"
                )
            }

            this.author(
                "${rootProject.name} | Version ${rootProject.version}",
                url,
                "https://raw.githubusercontent.com/RyderBelserion/assets/main/crazycrew/png/${rootProject.name}Website.png"
            )
        }

        this.embed {
            this.color(changeLogs)

            this.title("What changed?")

            this.description("""
                » Fixed an issue with message/command cooldowns not working.
                
                API:
                 » N/A
                 
                Bugs:
                 » Submit any bugs @ https://github.com/Crazy-Crew/ChatManager/issues
                
                Full Changelog -> $download
                
            """.trimIndent())
        }
    }

    this.url("SECOND_WEBHOOK")
}