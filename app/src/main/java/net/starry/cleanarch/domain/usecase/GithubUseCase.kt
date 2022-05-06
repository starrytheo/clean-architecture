package net.starry.cleanarch.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import net.starry.cleanarch.domain.ErrorHandler
import net.starry.cleanarch.domain.NetResult
import net.starry.cleanarch.domain.model.GitFork
import net.starry.cleanarch.domain.model.GitRepo
import net.starry.cleanarch.domain.model.GitUser
import net.starry.cleanarch.domain.repository.GithubRepository


class GetUserReposUseCase(
    private val githubRepository: GithubRepository,
    private val errorHandler: ErrorHandler
) : BaseUseCase<Flow<NetResult<Pair<GitUser, List<GitRepo>>>>, GetUserReposUseCase.Params>() {

    override suspend fun execute(params: Params) =
        githubRepository.getUser(params.userName).zip(githubRepository.getRepositories(params.userName)) { user, repositories ->
            Pair(user, repositories.sortedByDescending { it.starCount })
        }.toResult(errorHandler)

    data class Params(
        val userName: String
    )
}

// TODO . GitHub 관련 Use case class 추가.

//class GetRepoDetailUseCase(
//    private val githubRepository: GithubRepository,
//    private val errorHandler: ErrorHandler
//) : BaseUseCase<Flow<NetResult<Pair<GitRepo, List<GitFork>>>>, GetRepoDetailUseCase.Params>() {
//
//    override suspend fun execute(params: Params) =
//        githubRepository.getRepository(params.userName, params.id)
//            .zip(githubRepository.getForks(params.userName, params.id)) { repository, forks ->
//                Pair(repository, forks)
//            }.toResult(errorHandler)
//
//    data class Params(
//        val userName: String,
//        val id: String
//    )
//}
