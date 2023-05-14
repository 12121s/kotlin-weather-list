package com.illis.weatherlist.di

import com.google.gson.GsonBuilder
import com.illis.weatherlist.const.ServerConsts
import com.illis.weatherlist.data.source.remote.interceptor.ErrorInterceptor
import com.illis.weatherlist.data.source.remote.interceptor.MockInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideHttpInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (ServerConsts.TEST_MODE) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideMockInterceptor(): MockInterceptor {
        return MockInterceptor(listOf(ServerConsts.MOCK_WEATHER))
    }

    @Provides
    @Singleton
    fun provideErrorInterceptor(): ErrorInterceptor {
        return ErrorInterceptor()
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        mockInterceptor: MockInterceptor,
        errorInterceptor: ErrorInterceptor
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(mockInterceptor)
            addInterceptor(errorInterceptor)
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
        }
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .create()
        )
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient.Builder,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ServerConsts.API_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(client.build())
            .build()
    }

}