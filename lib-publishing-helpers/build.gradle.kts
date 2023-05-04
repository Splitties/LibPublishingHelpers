plugins {
    java
    kotlin("jvm") version "1.7.10"
    `maven-publish`
    signing
}

group = "com.louiscad.incubator"
version = "0.2.5"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
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
