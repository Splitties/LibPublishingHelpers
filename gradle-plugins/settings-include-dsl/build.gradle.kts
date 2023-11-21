plugins {
    id("gradle-plugin")
    `kotlin-dsl`
}

group = "org.splitties.gradle"

gradlePlugin {
    website = "https://github.com/Splitties/LibPublishingHelpers/tree/main/gradle-plugins/settings-include-dsl"
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
