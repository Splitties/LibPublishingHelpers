plugins {
    id("com.gradle.plugin-publish")
}

dependencies {
    compileOnly(gradleKotlinDsl())
}

gradlePlugin {
    vcsUrl = Publishing.gitUrl
}
