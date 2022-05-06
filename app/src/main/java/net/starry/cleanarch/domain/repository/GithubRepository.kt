package net.starry.cleanarch.domain.repository

import kotlinx.coroutines.flow.Flow
import net.starry.cleanarch.domain.model.GitFork
import net.starry.cleanarch.domain.model.GitRepo
import net.starry.cleanarch.domain.model.GitUser

interface GithubRepository {

    suspend fun getForks(userName: String, id: String): Flow<List<GitFork>>
    suspend fun getRepositories(userName: String): Flow<List<GitRepo>>
    suspend fun getRepository(userName: String, id: String): Flow<GitRepo>
    suspend fun getUser(userName: String): Flow<GitUser>


}

