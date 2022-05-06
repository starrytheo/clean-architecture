package net.starry.cleanarch.data.repository

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import net.starry.cleanarch.data.mapper.ForkEntityMapper
import net.starry.cleanarch.data.mapper.RepoEntityMapper
import net.starry.cleanarch.data.mapper.UserEntityMapper
import net.starry.cleanarch.data.remote.api.GithubApi
import net.starry.cleanarch.domain.repository.GithubRepository

@FlowPreview
class GithubRepositoryImpl(
    private val githubApi: GithubApi,
    private val userEntityMapper: UserEntityMapper,
    private val repoEntityMapper: RepoEntityMapper,
    private val forkEntityMapper: ForkEntityMapper,
) : GithubRepository {

    override suspend fun getRepository(userName: String, id: String) = flow {
        emit(
            githubApi.getRepository(
                userName = userName,
                repoName = id
            )
        )
    }.map {
        repoEntityMapper.mapFromRemote(it)
    }

    override suspend fun getRepositories(userName: String) = flow {
        emit(
            githubApi.getRepositories(userName)
        )
    }.map { repositories ->
        repositories.map {
            repoEntityMapper.mapFromRemote(it)
        }
    }

    override suspend fun getForks(userName: String, id: String) = flow {
        emit(
            githubApi.getForks(
                userName = userName,
                repoName = id
            )
        )
    }.map { forks ->
        forks.map {
            forkEntityMapper.mapFromRemote(it)
        }
    }

    override suspend fun getUser(userName: String) = flow {
        emit(
            githubApi.getUser(userName)
        )
    }.map {
        userEntityMapper.mapFromRemote(it)
    }
}