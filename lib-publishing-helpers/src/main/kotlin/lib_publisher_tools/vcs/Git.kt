package lib_publisher_tools.vcs

import lib_publisher_tools.process.execute
import lib_publisher_tools.process.executeAndPrint
import java.io.File
import kotlin.time.*

val Vcs.Companion.git: Vcs get() = Git

@OptIn(ExperimentalTime::class)
private object Git : Vcs {

    override fun didFileChange(file: File): Boolean {
        try {
            "git diff HEAD --exit-code ${file.path}".execute()
        } catch (ignored: Exception) {
            return true // Exit code is 1 (translated to an exception) when file changed
        }
        return false // Exit code is 0 if not changed.
    }

    override fun isOnBranch(expectedBranchName: String): Boolean {
        val currentBranch = "git rev-parse --abbrev-ref HEAD".execute().trimEnd()
        return currentBranch == expectedBranchName
    }

    override fun commitAllFiles(commitMessage: String) {
        "git commit -am \"$commitMessage\"".executeAndPrint()
    }

    override fun tagAnnotated(tag: String, annotationMessage: String) {
        "git tag -a $tag -m \"$annotationMessage\"".executeAndPrint()
    }

    override fun deleteTag(tag: String) {
        "git tag --delete $tag".executeAndPrint()
    }

    override fun checkoutBranch(branchName: String) {
        "git checkout $branchName".executeAndPrint()
    }

    override fun pull(repository: String) {
        "git pull $repository".executeAndPrint()
    }

    override fun push(repository: String, withTags: Boolean) {
        if (withTags) {
            "git push --tags $repository"
        } else {
            "git push $repository"
        }.executeAndPrint()
    }

    override fun getRemoteUrl(repository: String) = "git remote get-url --all $repository".execute()
    override fun getRemoteFetchUrl(repository: String) = "git remote get-url $repository".execute()
    override fun getRemotePushUrl(repository: String) = "git remote get-url --push $repository".execute()

    override fun mergeBranchIntoCurrent(sourceBranch: String) {
        "git merge $sourceBranch".executeAndPrint()
    }

    override fun getTags() = "git tag".execute().trimEnd().lineSequence()
}
