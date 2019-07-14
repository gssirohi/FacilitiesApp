package com.gojek.mygojekapp.injection.module

import com.gojek.data.repository.RemoteRepository
import com.gojek.mygojekapp.BuildConfig
import com.gojek.remote.RemoteRepositoryImpl
import com.gojek.remote.WeatherService
import com.gojek.remote.WeatherServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {
    //In order to provide dependencies statically
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideWeatherService():WeatherService{
            return WeatherServiceFactory.makeWeatherService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindRemoteRepositoryImpl(remoteRepositoryImpl:RemoteRepositoryImpl):RemoteRepository
}