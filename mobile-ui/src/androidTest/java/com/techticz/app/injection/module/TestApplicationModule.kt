package com.techticz.app.injection.module


import android.app.Application
import android.content.Context
import com.nhaarman.mockito_kotlin.mock
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
abstract class TestApplicationModule {

    @Binds
    abstract fun bindContext(application: Application): Context
}