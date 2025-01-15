plugins {
    id("root-plugin")
}

rootProject.group = "me.h1dd3nxn1nja.chatmanager"

val buildNumber: String? = System.getenv("BUILD_NUMBER")

rootProject.version = if (buildNumber != null) "${libs.versions.minecraft.get()}-$buildNumber" else "4.0.3"