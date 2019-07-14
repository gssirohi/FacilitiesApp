package com.gojek.data.repository

import com.gojek.data.model.ForecastDataEntity
import io.reactivex.Flowable

interface WeatherDataStore {
    fun getForecast(location:String,days:Int):Flowable<ForecastDataEntity>
}