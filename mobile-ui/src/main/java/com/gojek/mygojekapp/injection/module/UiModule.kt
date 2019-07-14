package com.gojek.mygojekapp.injection.module

import com.gojek.mygojekapp.forecast.ForecastActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule{

    @ContributesAndroidInjector
    abstract fun contributeForecastActivity():ForecastActivity
}