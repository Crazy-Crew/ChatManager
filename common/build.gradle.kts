val mcVersion = libs.versions.bundle.get()

dependencies {
    paperweight.paperDevBundle(mcVersion)

    compileOnly(libs.config.me)
}