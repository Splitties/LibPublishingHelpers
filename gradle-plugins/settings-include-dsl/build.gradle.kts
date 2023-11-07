plugins {
    `gradle-plugin`
    `kotlin-dsl`
}

group = "org.splitties.incubator"

gradlePlugin {
    plugins {
        create(project.name) {
            id = "org.splitties.incubator.settings-include-dsl"
            displayName = "Settings include DSL"
            description = "For Gradle projects with nested modules where you call include a lot."
            implementationClass = "org.splitties.incubator.gradle.SettingsIncludeDslPlugin"
        }
    }
}

kotlin {
    jvmToolchain(8)
}

pluginBundle {
    website = "https://github.com/LouisCAD/LibPublishingHelpers"
    vcsUrl = "https://github.com/LouisCAD/LibPublishingHelpers.git"
    tags = listOf("kotlin-dsl", "kotlin")
}
