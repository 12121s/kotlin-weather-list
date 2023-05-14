package com.illis.weatherlist.const

import com.illis.weatherlist.BuildConfig

class ServerConsts {
    companion object {
        const val API_URL = "https://api.openweathermap.org/"
        const val API_KEY = BuildConfig.API_KEY_NAME
        const val MOCK_WEATHER = "mock_weather"

        const val CITY_NAME_SEOUL = "Seoul"
        const val CITY_NAME_LONDON = "London"
        const val CITY_NAME_Chicago = "Chicago"

        const val SERVER_SOCKET_ERROR = 5060
        const val UNKNOWN_SERVER_ERROR = 4060

        const val TEST_MODE = true
    }
}