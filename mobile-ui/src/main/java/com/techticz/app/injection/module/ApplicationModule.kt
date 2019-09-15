package com.techticz.app.injection.module

import android.app.Application
import android.content.Context
import com.techticz.app.TechticzApplication
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {
    @Binds
    abstract fun bindContext(application:Application):Context

}