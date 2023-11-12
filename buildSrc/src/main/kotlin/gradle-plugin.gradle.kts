import gradle.kotlin.dsl.accessors._3446ec0516f3690bb745eb6b3e74e05f.signing
import publishing.trySignAll

plugins {
    id("com.gradle.plugin-publish")
    signing
}

dependencies {
    compileOnly(gradleKotlinDsl())
}

java { withSourcesJar() }
signing { trySignAll() }
