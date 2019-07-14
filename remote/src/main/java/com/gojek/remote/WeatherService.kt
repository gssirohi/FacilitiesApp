package com.gojek.remote

import com.gojek.remote.model.ForecastResponse
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


}