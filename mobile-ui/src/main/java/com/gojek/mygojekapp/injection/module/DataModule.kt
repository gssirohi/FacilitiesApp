package com.gojek.mygojekapp.injection.module

import com.gojek.data.WeatherDataManagerImpl
import com.gojek.domain.datamanager.WeatherDataManager
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule{
    @Binds
    abstract fun bindWeatherDataManager(dataManagerImpl: WeatherDataManagerImpl): WeatherDataManager
}