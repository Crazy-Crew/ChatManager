import com.ryderbelserion.feather.feather

plugins {
    id("com.ryderbelserion.feather-logic") version "0.0.3"

    `kotlin-dsl`
}

dependencies {
    // For the webhook tasks, this applies to the buildSrc only
    val ktor = "2.3.12"

    implementation("io.ktor","ktor-client-content-negotiation", ktor)
    implementation("io.ktor","ktor-serialization-gson", ktor)
    implementation("io.ktor","ktor-client-core-jvm", ktor)
    implementation("io.ktor","ktor-client-cio-jvm", ktor)

    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.8.1")

    feather("0.0.3")
}