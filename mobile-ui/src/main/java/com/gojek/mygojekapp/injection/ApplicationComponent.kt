package com.gojek.mygojekapp.injection

import android.app.Application
import com.gojek.mygojekapp.WeatherApplication
import dagger.BindsInstance
import dagger.Component

interface ApplicationComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application):Builder
        fun build():ApplicationComponent
    }

    fun inject(aplication:WeatherApplication)
}