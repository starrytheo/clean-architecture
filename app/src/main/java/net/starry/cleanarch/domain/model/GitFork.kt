package net.starry.cleanarch.domain.model

data class GitFork(
    val name: String,
    val fullName: String,
    val owner: GitUser
)
