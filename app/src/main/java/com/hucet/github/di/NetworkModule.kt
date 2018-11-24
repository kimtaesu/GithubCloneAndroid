package com.hucet.github.di

import com.hucet.github.BuildConfig
import com.hucet.github.api.GithubApi
import com.hucet.github.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [])
class NetworkModule {
    @Singleton
    @Provides
    fun provideServiceOkHttpClient(): OkHttpClient.Builder {
        val builer = OkHttpClient.Builder()
            .addLoggingIntercept()
            .setTimeouts()
        return builer
    }

    @Provides
    @Singleton
    fun provideIroboApi(okHttpClient: OkHttpClient.Builder): GithubApi = Retrofit.Builder()
        .client(okHttpClient.build())
        .baseUrl(BuildConfig.GITHUB_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()
        .create(GithubApi::class.java)
}

fun OkHttpClient.Builder.addLoggingIntercept(
    level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
): OkHttpClient.Builder {
    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.level = level
        addInterceptor(logging)
    }
    return this
}

fun OkHttpClient.Builder.setTimeouts(milliSecond: Long = BuildConfig.NETWORK_TIMEOUT_MILLIS): OkHttpClient.Builder {
    connectTimeout(milliSecond, TimeUnit.MILLISECONDS)
    readTimeout(milliSecond, TimeUnit.MILLISECONDS)
    writeTimeout(milliSecond, TimeUnit.MILLISECONDS)
    return this
}