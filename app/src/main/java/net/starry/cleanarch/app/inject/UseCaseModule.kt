package net.starry.cleanarch.app.inject

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import net.starry.cleanarch.domain.ErrorHandler
import net.starry.cleanarch.domain.repository.GithubRepository
import net.starry.cleanarch.domain.usecase.GetUserReposUseCase


@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetUserReposUseCase(repo: GithubRepository, errorHandler: ErrorHandler) =
        GetUserReposUseCase(repo, errorHandler)



}