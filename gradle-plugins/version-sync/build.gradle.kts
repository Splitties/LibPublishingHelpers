plugins {
    `gradle-plugin`
    `kotlin-dsl`
}

group = "org.splitties.incubator"

gradlePlugin {
    plugins {
        create(project.name) {
            id = "org.splitties.incubator.version-sync"
            displayName = "Sync version in all projects"
            description = "For library or Gradle plugin projects where you want to centralize where the version is defined."
            implementationClass = "org.splitties.incubator.gradle.VersionSyncPlugin"
        }
    }
}

kotlin {
    jvmToolchain(8)
}

pluginBundle {
    website = "https://github.com/LouisCAD/LibPublishingHelpers/tree/main/gradle-plugins/version-sync"
    vcsUrl = "https://github.com/LouisCAD/LibPublishingHelpers.git"
    tags = listOf("kotlin-dsl", "kotlin", "versioning", "multi-modules")
}
