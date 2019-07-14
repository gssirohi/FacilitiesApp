package com.gojek.data

import com.gojek.data.mapper.ForeCastDataMapper
import com.gojek.data.source.WeatherDataStoreFactory
import com.gojek.domain.datamanager.WeatherDataManager
import com.gojek.domain.model.ForecastData
import io.reactivex.Flowable
import javax.inject.Inject

class WeatherDataManagerImpl @Inject constructor(private val factory:WeatherDataStoreFactory,
                                                 private val domainMapper:ForeCastDataMapper): WeatherDataManager {
    override fun getForecast(location: String, days: Int): Flowable<ForecastData> {
        return factory.retrieveDataStore().getForecast(location,days).flatMap {
            Flowable.just(domainMapper.mapFromEntity(it))
        }
    }
}