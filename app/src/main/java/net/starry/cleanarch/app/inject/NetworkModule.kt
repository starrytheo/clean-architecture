package net.starry.cleanarch.app.inject

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.starry.cleanarch.BuildConfig
import net.starry.cleanarch.app.Constants.DEFAULT_HTTP_TIMEOUT
import net.starry.cleanarch.data.NetErrorHandlerImpl
import net.starry.cleanarch.data.remote.api.GithubApi
import net.starry.cleanarch.domain.ErrorHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    fun makeHttpClient() = OkHttpClient.Builder()
        .connectTimeout(DEFAULT_HTTP_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(DEFAULT_HTTP_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(DEFAULT_HTTP_TIMEOUT, TimeUnit.SECONDS)
        .apply {
            if (!BuildConfig.DEBUG) return@apply
            addInterceptor(HttpLoggingInterceptor()
                .apply { level = HttpLoggingInterceptor.Level.BODY }
            )
        }
        // .cache(Cache(context.cacheDir, NETWORK_CACHE_MAX_SIZE))

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        makeHttpClient().build()

    private fun OkHttpClient.retrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .client(this)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubApi(http: OkHttpClient): GithubApi =
        http.retrofit(GithubApi.BASE_URL).create(GithubApi::class.java)


    @Provides
    @Singleton
    fun provideErrorHandler(http: OkHttpClient): ErrorHandler =
        NetErrorHandlerImpl(http.retrofit(GithubApi.BASE_URL))

}
