package lib_publisher_tools.vcs

import java.io.File

/**
 * VCS stands for Version Control System.
 *
 * As of 2021/07, **git** has been the most popular one for years, if not decades.
 */
interface Vcs {
    companion object;

    fun didFileChange(file: File): Boolean
    fun isOnBranch(expectedBranchName: String): Boolean
    fun commitAllFiles(commitMessage: String)
    fun tagAnnotated(tag: String, annotationMessage: String)
    fun deleteTag(tag: String)
    fun checkoutBranch(branchName: String)
    fun checkoutAndTrackRemoteBranch(remoteName: String, branchName: String)
    fun fetch()
    fun pull(repository: String)
    fun push(repository: String, withTags: Boolean = false, setUpstream: Boolean = false, branchName: String? = null)
    fun getRemoteUrl(repository: String): String
    fun getRemoteFetchUrl(repository: String): String
    fun getRemotePushUrl(repository: String): String
    fun mergeBranchIntoCurrent(sourceBranch: String)
    fun getTags(): Sequence<String>
    fun getCurrentBranch(): String
    fun getBranches(): Sequence<String>
    fun getRemoteBranches(): Sequence<String>
    fun createBranch(branchName: String)
    fun createAndCheckoutBranch(branchName: String)
}

fun Vcs.isOnMainBranch() = isOnBranch(expectedBranchName = "main")
fun Vcs.checkoutMain() = checkoutBranch(branchName = "main")
fun Vcs.pullFromOrigin() = pull(repository = "origin")
fun Vcs.pushToOrigin(withTags: Boolean = false) = push(repository = "origin", withTags = withTags)
fun Vcs.mergeMainIntoCurrent() = mergeBranchIntoCurrent(sourceBranch = "main")
fun Vcs.hasBranch(branchName: String): Boolean = branchName in getBranches()
fun Vcs.hasRemoteBranch(fullBranchName: String): Boolean = fullBranchName in getRemoteBranches()
fun Vcs.hasRemoteBranch(
    remoteName: String,
    branchName: String
): Boolean = hasRemoteBranch(fullBranchName = "$remoteName/$branchName")

fun Vcs.hasTag(tagName: String): Boolean = getTags().any { it == tagName }
