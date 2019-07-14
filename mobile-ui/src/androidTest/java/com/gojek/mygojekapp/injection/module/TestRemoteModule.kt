package com.gojek.mygojekapp.injection.module

import com.gojek.data.repository.RemoteRepository
import com.gojek.remote.WeatherService
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object TestRemoteModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideRemoteRepository(): RemoteRepository {
        return mock()
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideWeatherService(): WeatherService {
        return mock()
    }
}