plugins {
    alias(libs.plugins.shadow)

    id("root-plugin")
}

project.description = "Platform independent version of ChatManager!"
project.group = "${rootProject.group}.core"

dependencies {
    compileOnly(libs.bundles.adventure)

    compileOnly(libs.fusion.core)

    compileOnlyApi(libs.jalu)
}