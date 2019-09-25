package com.techticz.app.injection.module

import com.techticz.data.repository.RemoteRepository
import com.techticz.remote.VoiceBookService
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
    fun provideWeatherService(): VoiceBookService {
        return mock()
    }
}