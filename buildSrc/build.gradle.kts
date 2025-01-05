import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    `kotlin-dsl`
    kotlin("jvm") version "2.+"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://maven.kikugie.dev/releases")
}

dependencies {
    fun plugin(id: String, version: String) = "${id}:${id}.gradle.plugin:${version}"
    implementation(plugin("dev.kikugie.stonecutter", "0.5"))
    implementation("com.google.code.gson:gson:2.+")
}

gradlePlugin {
    plugins {
        create("gg.meza.stonecuttermod") {
            id = "gg.meza.stonecuttermod"
            implementationClass = "gg.meza.stonecuttermod.ModPlugin"
        }
    }
}

val compileKotlin: KotlinCompile by tasks

compileKotlin.compilerOptions {
    languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
    jvmTarget.set(JvmTarget.JVM_21)
    freeCompilerArgs.addAll(listOf("-opt-in=kotlin.ExperimentalStdlibApi", "-opt-in=kotlin.RequiresOptIn"))
}


java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
