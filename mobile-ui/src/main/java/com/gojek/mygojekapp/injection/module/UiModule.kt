package com.gojek.mygojekapp.injection.module

import com.gojek.mygojekapp.UiThread
import com.gojek.mygojekapp.forecast.ForecastActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread

@Module
abstract class UiModule{

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributeForecastActivity():ForecastActivity
}