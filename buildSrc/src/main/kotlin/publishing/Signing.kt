package publishing

import gradle.kotlin.dsl.accessors._08d0b7ba318b04adf2094ca092a1d609.publishing
import org.gradle.api.Project
import org.gradle.plugins.signing.SigningExtension
import propertyOrEnv
import propertyOrEnvOrNull

context (Project)
fun SigningExtension.trySignAll() {
    useInMemoryPgpKeys(
        propertyOrEnvOrNull("GPG_key_id"), // If using a sub-key.
        propertyOrEnvOrNull("GPG_private_key") ?: return,
        propertyOrEnv("GPG_private_password")
    )
    sign(publishing.publications)
}
