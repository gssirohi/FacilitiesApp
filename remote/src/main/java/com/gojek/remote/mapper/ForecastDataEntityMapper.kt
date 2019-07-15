package com.gojek.remote.mapper

import com.gojek.data.model.ForecastDataEntity
import com.gojek.remote.WeatherService
import com.gojek.remote.model.ForecastResponse
import javax.inject.Inject

open class ForecastDataEntityMapper @Inject constructor():EntityMapper<ForecastResponse,ForecastDataEntity> {
    override fun mapFromRemote(type: ForecastResponse): ForecastDataEntity {
        var mutableList = mutableListOf<Float>()
        type.forecast.forecastday.forEach{
            mutableList.add(it.day.avgtemp_c)
        }
        var entity  = ForecastDataEntity(
            type.location.name,
            type.current.temp_c,
            mutableList
        )
        return entity
    }
}