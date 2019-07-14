package com.gojek.mygojekapp.injection.module

import com.gojek.data.WeatherDataManagerImpl
import com.gojek.data.executor.JobExecutor
import com.gojek.domain.datamanager.WeatherDataManager
import dagger.Binds
import dagger.Module
import org.buffer.android.boilerplate.domain.executor.ThreadExecutor

@Module
abstract class DataModule{
    @Binds
    abstract fun bindWeatherDataManager(dataManagerImpl: WeatherDataManagerImpl): WeatherDataManager

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}