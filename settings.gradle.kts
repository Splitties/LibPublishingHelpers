rootProject.name = "LibPublishingHelpers"
include("lib-publishing-helpers")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

val versionOfThisProject: String = file("version.txt").bufferedReader().use { it.readLine() }

gradle.beforeProject {
    version = versionOfThisProject
}
