pluginManagement {
    repositories {
        google() // ✅ Obligatoire pour les plugins Android
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        id("com.android.application") version "8.1.0"
        id("com.android.library") version "8.1.0"
        id("org.jetbrains.kotlin.android") version "1.9.10"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SignalementApp"
include(":app")
