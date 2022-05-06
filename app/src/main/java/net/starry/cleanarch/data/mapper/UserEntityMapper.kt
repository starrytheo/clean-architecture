package net.starry.cleanarch.data.mapper

import net.starry.cleanarch.data.model.UserModel
import net.starry.cleanarch.domain.model.GitUser

class UserEntityMapper : EntityMapper<UserModel, GitUser> {
    override fun mapFromRemote(model: UserModel) = GitUser(
        model.login,
        model.avatar_url
    )
}
