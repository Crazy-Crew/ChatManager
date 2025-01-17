plugins {
    id("root-plugin")
}

val buildNumber: String? = System.getenv("BUILD_NUMBER")

rootProject.version = if (buildNumber != null) "${libs.versions.minecraft.get()}-$buildNumber-5.0.0" else "5.0.0"
rootProject.group = "com.ryderbelserion.chatmanager"