package net.starry.cleanarch.data.remote.api;

import net.starry.cleanarch.data.model.ForkModel
import net.starry.cleanarch.data.model.RepoModel
import net.starry.cleanarch.data.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    companion object {
        const val BASE_URL = "https://api.github.com"
    }

    @GET("/users/{userName}")
    suspend fun getUser(
        @Path("userName") userName: String
    ): UserModel

    @GET("/users/{userName}/repos")
    suspend fun getRepositories(
        @Path("userName") userName: String
    ): List<RepoModel>

    @GET("/repos/{userName}/{repo}")
    suspend fun getRepository(
        @Path("userName") userName: String,
        @Path("repo") repoName: String
    ): RepoModel

    @GET("/repos/{userName}/{repo}/forks")
    suspend fun getForks(
        @Path("userName") userName: String,
        @Path("repo") repoName: String
    ): List<ForkModel>

}