package lib_publisher_tools.vcs

import lib_publisher_tools.process.NonZeroExitCodeException
import lib_publisher_tools.process.execute
import lib_publisher_tools.process.executeAndPrint
import java.io.File

val Vcs.Companion.git: Vcs get() = Git

private object Git : Vcs {

    override fun didFileChange(file: File): Boolean {
        try {
            "git diff HEAD --exit-code ${file.path}".execute()
        } catch (e: NonZeroExitCodeException) {
            when (e.value) {
                1 -> return true // Exit code is 1 when file changed.
                else -> throw e
            }
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

    override fun checkoutAndTrackRemoteBranch(remoteName: String, branchName: String) {
        "git checkout --track $remoteName/$branchName".executeAndPrint()
    }

    override fun fetch() {
        "git fetch".executeAndPrint()
    }

    override fun fetch(repository: String, sourceBranch: String, destinationBranch: String) {
        "git fetch $repository $sourceBranch:$destinationBranch".executeAndPrint()
    }

    override fun pull(repository: String) {
        "git pull $repository".executeAndPrint()
    }

    override fun push(repository: String, withTags: Boolean, setUpstream: Boolean, branchName: String?) {
        buildString {
            append("git push")
            if (withTags) append(" --tags")
            if (setUpstream) append(" --set-upstream")
            append(' '); append(repository)
            if (branchName != null) {
                append(' '); append(branchName)
            }
        }.executeAndPrint()
    }

    override fun getRemoteUrl(repository: String) = "git remote get-url --all $repository".execute()
    override fun getRemoteFetchUrl(repository: String) = "git remote get-url $repository".execute()
    override fun getRemotePushUrl(repository: String) = "git remote get-url --push $repository".execute()

    override fun mergeBranchIntoCurrent(sourceBranch: String) {
        "git merge $sourceBranch".executeAndPrint()
    }

    override fun getTags() = "git tag".execute().trimEnd().lineSequence()

    override fun getCurrentBranch() = "git branch --show-current".execute().trimEnd()

    override fun getBranches() = "git branch".execute().trimEnd().lineSequence().map {
        it.substringAfter("* ").trimStart()
    }

    override fun getRemoteBranches() = "git branch -r".execute().trimEnd().lineSequence().mapNotNull {
        if (it.startsWith("warning: ")) null
        else it.trimStart()
    }

    override fun createBranch(branchName: String) = "git branch $branchName".executeAndPrint()

    override fun createAndCheckoutBranch(branchName: String) = "git checkout -b $branchName".executeAndPrint()
}
