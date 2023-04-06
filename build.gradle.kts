import com.lordcodes.turtle.shellRun
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
                » Removed 1.8.8-1.17.1 support.
                » Likely will only be supporting https://papermc.io from this point on.
                
                API:
                 » All methods handling data in the features has been moved to **com.ryderbelserion.chatmanager.api**
                 » It can be accessed via our static instance of the plugin i.e ChatManager.getPlugin().api().whateveryouwanttouse
                 » Warning: It likely will change in the future so don't get to comfy with it.
                 
                Bugs:
                 » Submit any bugs @ https://github.com/Crazy-Crew/ChatManager/issues
                 » **Don't throw this into production instantly. I changed a LOT and would appreciate if you can test it.**
            """.trimIndent())
        }
    }

    this.url("DISCORD_WEBHOOK")
}