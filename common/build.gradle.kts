plugins {
    `config-java`
}

project.description = "Platform independent version of ChatManager!"
project.group = "${rootProject.group}.core"

dependencies {
    api(project(":chatmanager-api"))

    compileOnly(libs.bundles.adventure)

    compileOnly(libs.fusion.core)
}