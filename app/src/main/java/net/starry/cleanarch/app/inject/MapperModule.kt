package net.starry.cleanarch.app.inject

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import net.starry.cleanarch.data.mapper.ForkEntityMapper
import net.starry.cleanarch.data.mapper.RepoEntityMapper
import net.starry.cleanarch.data.mapper.UserEntityMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideUserEntityMapper() = UserEntityMapper()

    @Provides
    @Singleton
    fun provideRepoEntityMapper() = RepoEntityMapper()

    @Provides
    @Singleton
    fun provideForkEntityMapper(userEntityMapper : UserEntityMapper) = ForkEntityMapper(userEntityMapper)


}