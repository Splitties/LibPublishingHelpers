package lib_publisher_tools.versioning

/**
 * Check order is important. From least stable to most stable, then unknown
 */
fun Version.stabilityLevel(): StabilityLevel = stabilityLevel

private val knownVersionSuffixes = listOf("-android", "-jre")
private val knownStableKeywords = listOf("RELEASE", "FINAL", "GA")
private val digitsOnlyBasedVersionNumberRegex = "^[0-9,.v-]+$".toRegex()

private fun Version.isStable(): Boolean {
    val version = value
    val uppercaseVersion = version.uppercase()
    val hasStableKeyword = knownStableKeywords.any { it in uppercaseVersion }
    return hasStableKeyword || digitsOnlyBasedVersionNumberRegex.matches(version.withoutKnownSuffixes())
}

private fun Version.isMilestone(): Boolean {
    val version = value
    return when (val indexOfM = version.indexOfLast { it == 'M' }) {
        -1 -> false
        version.lastIndex -> false
        else -> version.substring(startIndex = indexOfM + 1).all { it.isDigit() }
    }
}

private fun String.withoutKnownSuffixes(): String {
    var result: String = this
    for (suffix in knownVersionSuffixes) {
        result = result.removeSuffix(suffix)
    }
    return result
}

