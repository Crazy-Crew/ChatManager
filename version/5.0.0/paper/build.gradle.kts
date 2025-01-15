plugins {
    id("paper-plugin")
}

val buildNumber: String? = System.getenv("BUILD_NUMBER")

project.group = "${rootProject.group}.paper"
project.version = if (buildNumber != null) "${libs.versions.minecraft.get()}-$buildNumber" else "5.0.0"
project.description = "The modern version of ChatManager written from the ground up!"