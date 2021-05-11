package lib_publisher_tools.vcs

import java.io.File

/**
 * VCS stands for Version Control System.
 *
 * As of 2020/04, **git** has been the most popular one for years if not decades.
 */
interface Vcs {
    companion object;

    fun didFileChange(file: File): Boolean
    fun isOnBranch(expectedBranchName: String): Boolean
    fun commitAllFiles(commitMessage: String)
    fun tagAnnotated(tag: String, annotationMessage: String)
    fun checkoutBranch(branchName: String)
    fun pull(repository: String)
    fun push(repository: String, withTags: Boolean = false)
    fun getRemoteUrl(repository: String): String
    fun getRemoteFetchUrl(repository: String): String
    fun getRemotePushUrl(repository: String): String
    fun mergeBranchIntoCurrent(sourceBranch: String)
    fun getTags(): Sequence<String>
}

fun Vcs.isOnDevelopBranch() = isOnBranch(expectedBranchName = "develop")
fun Vcs.checkoutMain() = checkoutBranch(branchName = "main")
fun Vcs.checkoutDevelop() = checkoutBranch(branchName = "develop")
fun Vcs.pullFromOrigin() = pull(repository = "origin")
fun Vcs.pushToOrigin(withTags: Boolean = false) = push(repository = "origin", withTags = withTags)
fun Vcs.mergeMainIntoCurrent() = mergeBranchIntoCurrent(sourceBranch = "main")
fun Vcs.mergeDevelopIntoCurrent() = mergeBranchIntoCurrent(sourceBranch = "develop")
