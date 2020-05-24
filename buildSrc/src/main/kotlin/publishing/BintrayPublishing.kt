@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import java.net.URI

fun PublishingExtension.bintrayRepositoryPublishing(
    project: Project,
    user: String,
    repo: String,
    bintrayPackage: String,
    publish: Boolean = false,
    override: Boolean = false
) {
    val repoUrl = buildString {
        append("https://api.bintray.com/maven/$user/$repo/$bintrayPackage/")
        append(";publish="); append(if (publish) '1' else '0')
        if (override) append(";override=1")
    }
    repositories {
        maven {
            url = URI(repoUrl)
            credentials {
                username = project.propertyOrEnv("bintray_user")
                password = project.propertyOrEnv("bintray_api_key")
            }
        }
    }
}
