import lib_publisher_tools.process.NonZeroExitCodeException
import lib_publisher_tools.vcs.Vcs
import lib_publisher_tools.vcs.git
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.absolute
import kotlin.io.path.div
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GitTest {

    private val git: Vcs = Vcs.git

    @Test
    fun `test didFileChange`() {
        val projectRootPath = Paths.get("").absolute().parent

        projectRootPath.resolve("LICENSE").toFile().let { licenseFile ->
            assertFalse(git.didFileChange(licenseFile))

            val initialContent = licenseFile.readText()
            licenseFile.writeText(initialContent.trimEnd())
            assertTrue(git.didFileChange(licenseFile))

            licenseFile.writeText(initialContent)
            assertFalse(git.didFileChange(licenseFile))
        }

        projectRootPath.resolve("gibberishklbstkpftw").toFile().let { nonExistingFile ->
            assertFailsWith<NonZeroExitCodeException> {
                git.didFileChange(nonExistingFile)
            }
        }
    }

    @Test
    fun `test getTags`() {
        val knownTags = listOf(
            "v0.1.0",
            "v0.1.2",
            "v0.1.4",
            "v0.1.5",
            "v0.2.0",
        )
        assertTrue(git.getTags().toList().containsAll(knownTags))
    }

    @Test
    fun `test getCurrentBranch`() {
        assertTrue(git.getCurrentBranch().isNotBlank())
    }

    @Test
    fun `test getBranches()`() {
        val knownBranches = listOf("main")
        assertTrue(git.getBranches().toList().containsAll(knownBranches))
    }

    @Test
    fun `test getRemoteBranches()`() {
        val knownRemoteBranches = listOf("origin/main")
        assertTrue(git.getRemoteBranches().toList().containsAll(knownRemoteBranches))
    }
}
