plugins {
    id("root-plugin")
}

rootProject.group = "me.h1dd3nxn1nja.chatmanager"

val buildNumber: String? = System.getenv("BUILD_NUMBER")
val isPublishing: String? = System.getenv("IS_PUBLISHING")

rootProject.version = if (buildNumber != null && isPublishing == null) "${libs.versions.minecraft.get()}-$buildNumber" else "4.0.5"