rootProject.name = "LibPublishingHelpers"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

val versionOfThisProject: String = file("version.txt").bufferedReader().use { it.readLine() }

gradle.beforeProject {
    version = versionOfThisProject
}

include {
    "lib-publishing-helpers"()
    "gradle-plugins" {
        "settings-include-dsl"()
        "version-sync"()
    }
}

//region include DSL
class ModuleParentScope(
    private val name: String,
    private val parent: ModuleParentScope? = null
) {

    operator fun String.invoke(block: (ModuleParentScope.() -> Unit)? = null) {
        check(startsWith(':').not())
        val moduleName = ":$this"
        val projectName = "$parentalPath$moduleName"
        include(projectName)
        block?.let { buildNode ->
            ModuleParentScope(
                name = moduleName,
                parent = this@ModuleParentScope
            ).buildNode()
        }
    }

    private val parentalPath: String =
        generateSequence(this) { it.parent }
            .map { it.name }.toList().reversed().joinToString("")

}

inline fun include(block: ModuleParentScope.() -> Unit) {
    ModuleParentScope("").block()
}
//endregion
