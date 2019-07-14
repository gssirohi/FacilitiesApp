package com.gojek.presentation.mapper

import com.gojek.domain.model.ForecastData
import com.gojek.presentation.model.ForecastModel
import javax.inject.Inject

class ForecastModelMapper @Inject constructor():ModelMapper<ForecastData,ForecastModel> {
    override fun mapFromData(type: ForecastData): ForecastModel {
        return ForecastModel(type.locationName,type.currentTemp,type.forecastTemp)
    }
}