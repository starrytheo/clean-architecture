package net.starry.cleanarch.app.inject

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.starry.cleanarch.data.mapper.ForkEntityMapper
import net.starry.cleanarch.data.mapper.RepoEntityMapper
import net.starry.cleanarch.data.mapper.UserEntityMapper
import net.starry.cleanarch.data.remote.api.GithubApi
import net.starry.cleanarch.data.repository.GithubRepositoryImpl
import net.starry.cleanarch.domain.repository.GithubRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGithubRepository(
        githubApi: GithubApi,
        userEntityMapper: UserEntityMapper,
        repoEntityMapper: RepoEntityMapper,
        forkEntityMapper: ForkEntityMapper
    ): GithubRepository =
        GithubRepositoryImpl(githubApi, userEntityMapper, repoEntityMapper, forkEntityMapper)

}
