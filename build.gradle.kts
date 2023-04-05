import com.lordcodes.turtle.shellRun
import task.WebhookExtension
import java.awt.Color

plugins {
    id("chatmanager.root-plugin")

    id("featherpatcher") version "8ef64e4"
}

val releaseUpdate = Color(27, 217, 106)
val betaUpdate = Color(255, 163, 71)
val changeLogs = Color(37, 137, 204)

val beta = settings.versions.beta.get().toBoolean()
val extension = settings.versions.extension.get()

val color = if (beta) betaUpdate else releaseUpdate
val repo = if (beta) "beta" else "releases"

val url = if (beta) "https://ci.crazycrew.us/job/${rootProject.name}/" else "https://modrinth.com/$extension/${rootProject.name.lowercase()}/versions"
val download = if (beta) "https://ci.crazycrew.us/job/${rootProject.name}/" else "https://modrinth.com/$extension/${rootProject.name.lowercase()}/version/${rootProject.version}"
val msg = if (beta) "New version of ${rootProject.name} is ready!" else "New version of ${rootProject.name} is ready! <@&929463441159254066>"

val hash = shellRun("git", listOf("rev-parse", "--short", "HEAD"))

rootProject.version = if (beta) hash else "3.9.1"

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
                Changes:
                » Added 1.19.4 support
                » Removed 1.8.8-1.18.2 support, It will be moved into another github branch.
                » Expect that the branch likely won't be maintained after a period of time.
                » Likely will only be supporting https://papermc.io from this point on.
            """.trimIndent())
        }
    }

    this.url("DISCORD_WEBHOOK")
}