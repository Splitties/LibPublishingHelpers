import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.72"
    `maven-publish`
    signing
}

group = "com.louiscad.incubator"
version = "0.1.4"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    kotlinOptions.jvmTarget = "1.6" //TODO: Switch back to 1.8 when https://youtrack.jetbrains.com/issue/KT-39211 is fixed.
}

java {
    withSourcesJar()
}

signing {
    useInMemoryPgpKeys(
        propertyOrEnvOrNull("GPG_key_id"),
        propertyOrEnvOrNull("GPG_private_key") ?: return@signing,
        propertyOrEnv("GPG_private_password")
    )
    sign(publishing.publications)
}

publishing {
    publications.withType<MavenPublication> {
        artifact(tasks.emptyJavadocJar())
        setupPom()
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
        mavenCentralStagingPublishing(
            project = project,
            repositoryId = System.getenv("sonatype_staging_repo_id")
        )
        sonatypeSnapshotsPublishing(project = project)
    }
}
