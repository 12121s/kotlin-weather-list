package com.illis.weatherlist.data.model

data class WeatherDto(
    val cod: String = "",
    val message: Int = 0,
    val cnt: Int = 0,
    val list: List<WeatherInfo> = listOf(),
    val city: City = City()
) {
    data class WeatherInfo(
        val dt: Long = 0,
        val main: Main = Main(),
        val weather: List<Weather> = listOf(),
        val clouds: Clouds = Clouds(),
        val wind: Wind = Wind(),
        val visibility: Int = 0,
        val pop: Double = 0.0,
        val rain: Rain? = null,
        val sys: Sys = Sys(),
        val dt_txt: String = ""
    ) {
        data class Main(
            val temp: Double = 0.0,
            val feels_like: Double = 0.0,
            val temp_min: Double = 0.0,
            val temp_max: Double = 0.0,
            val pressure: Int = 0,
            val sea_level: Int = 0,
            val grnd_level: Int = 0,
            val humidity: Int = 0,
            val temp_kf: Double = 0.0
        )
        data class Weather(
            val id: Int = 0,
            val main: String = "",
            val description: String = "",
            val icon: String = ""
        )
        data class Clouds(
            val all: Int = 0
        )
        data class Wind(
            val speed: Double = 0.0,
            val deg: Int = 0,
            val gust: Double = 0.0
        )
        data class Rain(
            val `3h`: Double = 0.0
        )
        data class Sys(
            val pod: String = ""
        )
    }
    data class City(
        val id: Int = 0,
        val name: String = "",
        val coord: Coord = Coord(),
        val country: String = "",
        val population: Int = 0,
        val timezone: Int = 0,
        val sunrise: Int = 0,
        val sunset: Int = 0
    ) {
        data class Coord(
            val lat: Double = 0.0,
            val lon: Double = 0.0
        )
    }
}
