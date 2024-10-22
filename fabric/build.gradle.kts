plugins {
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":common"))
}

tasks {
    assemble {
        dependsOn(shadowJar)

        doLast {
            copy {
                from(shadowJar.get())
                into(rootProject.projectDir.resolve("jars"))
            }
        }
    }

    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")
    }
}