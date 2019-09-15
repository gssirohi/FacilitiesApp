package com.techticz.app.injection.module

import com.techticz.data.repository.RemoteRepository
import com.techticz.app.BuildConfig
import com.techticz.remote.RemoteRepositoryImpl
import com.techticz.remote.VoiceBookService
import com.techticz.remote.VoiceBookServiceFactory
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
        fun provideWeatherService():VoiceBookService{
            return VoiceBookServiceFactory.makeWeatherService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindRemoteRepositoryImpl(remoteRepositoryImpl:RemoteRepositoryImpl):RemoteRepository
}