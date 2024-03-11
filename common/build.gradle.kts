plugins {
    id("root-plugin")
}

dependencies {
    api(libs.configme) {
        exclude("org.yaml", "snakeyaml")
    }
}