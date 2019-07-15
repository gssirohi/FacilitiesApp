package com.gojek.presentation.mapper

import com.gojek.domain.model.ForecastData
import com.gojek.presentation.model.ForecastModel
import javax.inject.Inject

open class ForecastModelMapper @Inject constructor():ModelMapper<ForecastData,ForecastModel> {
    open override fun mapFromData(type: ForecastData): ForecastModel {
        var forecastList = type.forecastTemp
        var mutableList = mutableListOf(forecastList)
        //oth item will be the today's temprature which will be show from currentTemp
        //so removing 0th item
        mutableList.removeAt(0)

        return ForecastModel(type.locationName,type.currentTemp,type.forecastTemp)
    }
}