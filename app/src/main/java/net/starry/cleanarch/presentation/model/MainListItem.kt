package net.starry.cleanarch.presentation.model

import net.starry.cleanarch.domain.model.GitRepo
import net.starry.cleanarch.domain.model.GitUser

sealed class MainListItem {
    data class Header(val user: GitUser) : MainListItem() {
        companion object
    }

    data class RepoItem(val repo: GitRepo) : MainListItem() {
        companion object
    }
}
