plugins {
    id("root-plugin")

    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
}

kotlin {
    jvmToolchain(17)

    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
            javaParameters = true
        }
    }
}