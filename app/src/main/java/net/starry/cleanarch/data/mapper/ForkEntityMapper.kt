package net.starry.cleanarch.data.mapper

import net.starry.cleanarch.data.model.ForkModel
import net.starry.cleanarch.domain.model.GitFork

class ForkEntityMapper(
    private val userMapper: UserEntityMapper
) : EntityMapper<ForkModel, GitFork> {
    override fun mapFromRemote(model: ForkModel) = GitFork(
        model.name,
        model.full_name,
        userMapper.mapFromRemote(model.owner)
    )
}
