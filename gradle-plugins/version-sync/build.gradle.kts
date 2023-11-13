plugins {
    `gradle-plugin`
    `kotlin-dsl`
}

group = "org.splitties.gradle"

gradlePlugin {
    website = "https://github.com/LouisCAD/LibPublishingHelpers/tree/main/gradle-plugins/version-sync"
    vcsUrl = "https://github.com/LouisCAD/LibPublishingHelpers.git"
    plugins {
        create(project.name) {
            id = "org.splitties.version-sync"
            displayName = "Sync version in all projects"
            description = "For library or Gradle plugin projects where you want to centralize where the version is defined."
            tags = listOf("kotlin-dsl", "kotlin", "versioning", "multi-modules")
            implementationClass = "org.splitties.gradle.VersionSyncPlugin"
        }
    }
}

kotlin {
    jvmToolchain(8)
}
