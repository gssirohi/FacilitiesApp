package com.gojek.remote

import com.gojek.data.model.ForecastDataEntity
import com.gojek.data.repository.RemoteRepository
import com.gojek.remote.mapper.ForecastDataEntityMapper
import io.reactivex.Flowable
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(val weatherService: WeatherService,val entityMapper: ForecastDataEntityMapper):RemoteRepository {
    override fun getForeCast(location: String, days: Int): Flowable<ForecastDataEntity> {
        return weatherService.getForecast(
            RemoteConstants.WEATHER_API_KEY,
            location,
            days
            )
            .map { val entity = entityMapper.mapFromRemote(it)
            entity}
    }
}