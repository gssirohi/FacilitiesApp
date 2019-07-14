package com.gojek.mygojekapp.mapper

import com.gojek.mygojekapp.model.ForecastUiModel
import com.gojek.presentation.model.ForecastModel
import javax.inject.Inject

class ForecastUiModelMapper @Inject constructor():UiMapper<ForecastUiModel,ForecastModel> {
    override fun mapToUiModel(type: ForecastModel): ForecastUiModel {
        return ForecastUiModel(type.locationName,type.currentTemp,type.forecastTemp)
    }
}