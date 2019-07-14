package com.gojek.mygojekapp.injection.module

import com.gojek.data.WeatherDataManagerImpl
import com.gojek.data.executor.JobExecutor
import com.gojek.domain.datamanager.WeatherDataManager
import com.gojek.domain.executor.ThreadExecutor
import dagger.Binds
import dagger.Module


@Module
abstract class DataModule{
    @Binds
    abstract fun bindWeatherDataManager(dataManagerImpl: WeatherDataManagerImpl): WeatherDataManager

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}