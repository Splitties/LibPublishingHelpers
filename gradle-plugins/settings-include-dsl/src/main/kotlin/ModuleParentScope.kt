package org.splitties.incubator.gradle

import org.gradle.api.initialization.Settings

class ModuleParentScope(
    private val settings: Settings,
    private val name: String,
    private val parent: ModuleParentScope? = null
) {

    operator fun String.invoke(block: (ModuleParentScope.() -> Unit)? = null) {
        check(startsWith(':').not())
        val moduleName = ":$this"
        val projectName = "$parentalPath$moduleName"
        settings.include(projectName)
        block?.let { buildNode ->
            ModuleParentScope(
                settings = settings,
                name = moduleName,
                parent = this@ModuleParentScope
            ).buildNode()
        }
    }

    private val parentalPath: String =
        generateSequence(this) { it.parent }
            .map { it.name }.toList().reversed().joinToString("")

}
