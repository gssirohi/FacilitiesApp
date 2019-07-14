package com.gojek.domain.datamanager

import com.gojek.domain.model.ForecastData
import io.reactivex.Flowable

interface WeatherDataManager {
    fun getForecast(location:String,days:Int): Flowable<ForecastData>
}