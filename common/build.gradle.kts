plugins {
    id("root-plugin")
}

project.group = "${rootProject.group}.common"
project.version = "${rootProject.version}"

dependencies {
    api(libs.config.me) {
        exclude("org.yaml", "snakeyaml")
    }
}

val component: SoftwareComponent = components["java"]

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = rootProject.group.toString()
                artifactId = "${rootProject.name.lowercase()}-${project.name.lowercase()}-api"
                version = rootProject.version.toString()

                from(component)
            }
        }
    }
}