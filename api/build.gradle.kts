plugins {
    id("chatmanager.base")
}

project.version = "0.1.0"

dependencies {
    compileOnly(libs.bundles.adventure)

    compileOnly(libs.fusion.core)

    compileOnly(libs.jetbrains)

    compileOnly(libs.jalu)
}