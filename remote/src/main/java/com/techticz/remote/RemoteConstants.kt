package com.techticz.remote

class RemoteConstants{
    companion object {
        const val WEATHER_API_KEY = "196a0ade20a146fba17171416190607"

        const val BASE_URL = "https://api.apixu.com/v1/"
        const val SUFFIX_CURR_WEATHER = "current.json"
        const val SUFFIX_VOICE_BOOKS = "forecast.json"
        const val NUMBER_OF_FORECAST_DAYS: Int = 5 //including current day
    }
}