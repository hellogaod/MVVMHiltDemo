package com.aregyan.github.di

import com.aregyan.github.BuildConfig
import com.aregyan.data.network.DemoApiService
import com.aregyan.data.network.UserDetailsService
import com.aregyan.data.network.UserListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        //.baseUrl("https://api.github.com/")//mvvm-hilt的地址
        .baseUrl("https://www.oschina.net/")//mvvm-habit的地址
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): UserListService =
        retrofit.create(UserListService::class.java)

    @Provides
    @Singleton
    fun provideUserDetailsService(retrofit: Retrofit): UserDetailsService =
        retrofit.create(UserDetailsService::class.java)

    @Provides
    @Singleton
    fun provideDemoApiService(retrofit: Retrofit): DemoApiService =
        retrofit.create(DemoApiService::class.java)
}