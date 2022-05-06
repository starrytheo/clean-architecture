package net.starry.cleanarch.data.mapper

import net.starry.cleanarch.data.model.RepoModel
import net.starry.cleanarch.domain.model.GitRepo

class RepoEntityMapper : EntityMapper<RepoModel, GitRepo> {
    override fun mapFromRemote(model: RepoModel) = GitRepo(
        model.name,
        model.description ?: "",
        model.stargazers_count.toString()
    )
}
