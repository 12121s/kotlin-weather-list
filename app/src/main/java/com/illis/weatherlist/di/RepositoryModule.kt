package com.illis.weatherlist.di

import com.illis.weatherlist.data.repository.WeatherRepositoryImpl
import com.illis.weatherlist.data.source.remote.api.WeatherApiService
import com.illis.weatherlist.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesWeatherRepository(
        weatherApiService: WeatherApiService
    ) : WeatherRepository = WeatherRepositoryImpl(weatherApiService)
}