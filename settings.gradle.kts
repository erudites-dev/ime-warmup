pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.neoforged.net/releases/")
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "ime-warmup"

includeBuild("build-logic")
include("common")
include("fabric")
include("neoforge")
