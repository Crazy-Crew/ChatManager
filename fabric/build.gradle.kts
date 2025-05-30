plugins {
    alias(libs.plugins.fabric.loom)

    `config-java`
}

project.description = "Fabric version of ChatManager!"
project.group = "${rootProject.group}.fabric"

dependencies {
    minecraft(libs.minecraft.get())
    mappings(loom.officialMojangMappings())

    modCompileOnly(libs.fabric.loader.get())
    modCompileOnly(libs.fabric.api.get())
}