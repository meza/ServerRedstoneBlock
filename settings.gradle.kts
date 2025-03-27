pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.kikugie.dev/releases")
        maven("https://maven.kikugie.dev/snapshots")
        maven("https://maven.terraformersmc.com/")
        maven("https://maven.shedaniel.me/")
    }
}
plugins {
    id("gg.meza.stonecraft") version "0.7-SNAPSHOT"
    id("dev.kikugie.stonecutter") version "0.5"
}

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true
    shared {
        fun mc(version: String, vararg loaders: String) {
            // Make the relevant version directories named "1.20.2-fabric", "1.20.2-forge", etc.
            for (it in loaders) vers("$version-$it", version)
        }

        mc("1.20.2", "fabric", "forge")
        mc("1.21", "fabric", "forge", "neoforge")
        mc("1.21.4", "fabric", "forge", "neoforge")
        mc("1.21.5", "fabric", "neoforge")

        vcsVersion = "1.21.5-fabric"
    }
    create(rootProject)
}

rootProject.name = "ServerRedstoneBlock"
