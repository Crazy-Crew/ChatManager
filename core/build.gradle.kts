plugins {
    id("chatmanager.base")
}

dependencies {
    compileOnly(libs.bundles.adventure)

    compileOnly(libs.fusion.core)

    compileOnly(libs.jetbrains)

    compileOnlyApi(libs.jalu)

    api(project(":api"))
}