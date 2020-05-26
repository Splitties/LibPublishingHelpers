import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.72"
    `maven-publish`
}

group = "com.louiscad.incubator"
version = "0.1.0"

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

publishing {
    setupPomForMavenPublications()
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
        bintrayRepositoryPublishing(
            project = project,
            user = "louiscad",
            repo = "maven",
            bintrayPackage = "lib-publishing-helpers",
            publish = true
        )
    }
}
