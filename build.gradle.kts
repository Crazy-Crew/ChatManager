plugins {
    id("chat.base")
}

val buildNumber: String? = System.getenv("BUILD_NUMBER")

rootProject.version = if (buildNumber != null) "${libs.versions.minecraft.get()}-$buildNumber" else "4.0.2"

subprojects.filter { it.name != "api" }.forEach {
    it.project.version = rootProject.version
}