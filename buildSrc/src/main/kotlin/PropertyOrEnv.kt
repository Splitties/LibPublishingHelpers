import org.gradle.api.Project

fun Project.propertyOrEnv(key: String): String? {
    return findProject(key) as String? ?: System.getenv(key)
}
