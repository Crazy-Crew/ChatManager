plugins {
    `config-java`
}

project.description = "The official api for ChatManager"
project.group = "${rootProject.group}.api"

dependencies {
    compileOnly(libs.bundles.adventure)

    compileOnly(libs.fusion.core)
}