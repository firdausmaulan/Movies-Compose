package com.fd.movie.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.fd.movie.BuildConfig
import com.fd.movie.data.remote.ApiService
import com.fd.movie.data.remote.AuthenticationInterceptor
import com.fd.movie.data.repositories.MovieRepository
import com.fd.movie.data.repositories.MovieRepositoryImpl
import com.fd.movie.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient {
        val chuckerInterceptor = ChuckerInterceptor.Builder(application)
            .build()
        return OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(Constants.APP_KEY))
            .addInterceptor(chuckerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideMovieRepository(service: ApiService): MovieRepository {
        return MovieRepositoryImpl(service)
    }
}
