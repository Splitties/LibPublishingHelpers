@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.withType

object Publishing {
    const val gitUrl = "https://github.com/LouisCAD/LibPublishingHelpers.git"
    const val siteUrl = "https://github.com/LouisCAD/LibPublishingHelpers"
    const val libraryDesc = "Tools for semi-auto library publishing (and other chores). Designed for Kotlin scripts."
}

fun PublishingExtension.setupPomForMavenPublications() {
    publications.withType<MavenPublication> {
        setupPom()
    }
}

@Suppress("UnstableApiUsage")
fun MavenPublication.setupPom(
    gitUrl: String = Publishing.gitUrl,
    siteUrl: String = Publishing.siteUrl,
    libraryDesc: String = Publishing.libraryDesc
) = pom {
    if (name.isPresent.not()) {
        name by artifactId
    }
    description by libraryDesc
    url by siteUrl
    licenses {
        license {
            name by "The Apache Software License, Version 2.0"
            url by "https://www.apache.org/licenses/LICENSE-2.0.txt"
        }
    }
    developers {
        developer {
            id by "louiscad"
            name by "Louis CAD"
            email by "louis.cognault@gmail.com"
        }
    }
    scm {
        connection by gitUrl
        developerConnection by gitUrl
        url by siteUrl
    }
}
