pluginManagement {
    includeBuild("convention-plugins")
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.splitties.settings-include-dsl") version "0.2.6"
    id("org.splitties.version-sync") version "0.2.6"
}

rootProject.name = "LibPublishingHelpers"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include {
    "lib-publishing-helpers"()
    "gradle-plugins" {
        "settings-include-dsl"()
        "version-sync"()
    }
}
