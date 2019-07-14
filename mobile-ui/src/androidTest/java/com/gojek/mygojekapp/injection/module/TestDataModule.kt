package com.gojek.mygojekapp.injection.module

import com.gojek.data.executor.JobExecutor
import com.gojek.domain.datamanager.WeatherDataManager
import com.gojek.domain.executor.ThreadExecutor
import com.nhaarman.mockito_kotlin.mock
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
abstract class TestDataModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        @Singleton
        fun provideWeatherDataManager(): WeatherDataManager {
            return mock()
        }
    }

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}