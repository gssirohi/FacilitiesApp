package com.gojek.remote

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET(RemoteConstants.SUFFIX_FORECAST_WEATHER)
    fun getForecast(
        @Query("key") key:String,
        @Query("q") q:String,
        @Query("days") days:Int
    ):Flowable<ForecastResponse>

    data class ForecastResponse(val location:WeatherLocation,val current:CurrentWeather, val forecast:ForeCast)

    data class WeatherLocation(val name:String)

    data class CurrentWeather(val temp_c:Float)

    data class ForeCast(val forecastday:List<DayForeCast>)

    data class DayForeCast(val date:String, val day:Day)

    data class Day(val avgtemp_c:Float)
}