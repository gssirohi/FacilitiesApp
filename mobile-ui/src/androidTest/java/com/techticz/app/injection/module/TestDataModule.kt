package com.techticz.app.injection.module

import com.techticz.data.executor.JobExecutor
import com.techticz.data.datamanager.DataManager
import com.techticz.domain.executor.ThreadExecutor
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
        fun provideWeatherDataManager(): DataManager {
            return mock()
        }
    }

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}