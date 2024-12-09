dependencies {
    compileOnly(libs.bundles.adventure)

    compileOnly(libs.jetbrains)

    compileOnly(libs.vital.api)

    compileOnlyApi(libs.jalu)

    api(project(":api"))
}