package com.gojek.data.mapper

import com.gojek.data.model.ForecastDataEntity
import com.gojek.domain.model.ForecastData
import javax.inject.Inject

open class ForeCastDataMapper @Inject constructor():DomainMapper<ForecastDataEntity,ForecastData>{
    override fun mapFromEntity(type: ForecastDataEntity): ForecastData {
        return ForecastData(type.locationName,type.currentTemp,type.forecastTemp)
    }

    override fun mapToEntity(type: ForecastData): ForecastDataEntity {
        return ForecastDataEntity(type.locationName,type.currentTemp,type.forecastTemp)
    }

}