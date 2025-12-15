plugins {
    `java-plugin`
}

project.description = "Platform independent version of ChatManager!"
project.group = "${rootProject.group}.core"

dependencies {
    api(project(":api"))

    implementation(libs.hikari.cp)
    //implementation(libs.postgres)

    compileOnly(libs.bundles.kyori)
    compileOnly(libs.fusion.core)
}