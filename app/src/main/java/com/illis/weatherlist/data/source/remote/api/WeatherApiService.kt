package com.illis.weatherlist.data.source.remote.api

import com.illis.weatherlist.data.model.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApiService {
    @Headers("Accept: application/json", "Content-Type: application/json; charset=utf-8")
    @GET("/data/2.5/forecast/")
    suspend fun getWeather(
        @Query("q") city_name: String,
        @Query("appid") api_key: String,
        @Query("units") units : String = "metric"
    ): Response<WeatherDto>
}