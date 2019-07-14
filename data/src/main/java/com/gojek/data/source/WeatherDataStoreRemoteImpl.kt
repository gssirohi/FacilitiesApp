package com.gojek.data.source

import com.gojek.data.model.ForecastDataEntity
import com.gojek.data.repository.RemoteRepository
import com.gojek.data.repository.WeatherDataStore
import io.reactivex.Flowable
import javax.inject.Inject

open class WeatherDataStoreRemoteImpl @Inject constructor(val remoteRepo: RemoteRepository):WeatherDataStore {
    override fun getForecast(location: String, days: Int): Flowable<ForecastDataEntity> {
        return remoteRepo.getForeCast(location,days)
    }
}