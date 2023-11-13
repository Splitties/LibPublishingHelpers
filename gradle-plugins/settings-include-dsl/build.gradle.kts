plugins {
    `gradle-plugin`
    `kotlin-dsl`
}

group = "org.splitties.gradle"

gradlePlugin {
    website = "https://github.com/LouisCAD/LibPublishingHelpers/tree/main/gradle-plugins/settings-include-dsl"
    vcsUrl = "https://github.com/LouisCAD/LibPublishingHelpers.git"
    plugins {
        create(project.name) {
            id = "org.splitties.settings-include-dsl"
            displayName = "Settings include DSL"
            description = "For Gradle projects with nested modules where you call include a lot."
            tags = listOf("kotlin-dsl", "kotlin")
            implementationClass = "org.splitties.gradle.SettingsIncludeDslPlugin"
        }
    }
}

kotlin {
    jvmToolchain(8)
}
