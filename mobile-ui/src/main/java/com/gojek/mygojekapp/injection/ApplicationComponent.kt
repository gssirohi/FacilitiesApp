package com.gojek.mygojekapp.injection

import android.app.Application
import com.gojek.mygojekapp.WeatherApplication
import com.gojek.mygojekapp.injection.module.ApplicationModule
import com.gojek.mygojekapp.injection.module.UiModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = arrayOf(ApplicationModule::class,UiModule::class))
interface ApplicationComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application):Builder
        fun build():ApplicationComponent
    }

    fun inject(aplication:WeatherApplication)
}