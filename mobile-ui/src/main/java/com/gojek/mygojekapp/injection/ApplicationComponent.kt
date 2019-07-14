package com.gojek.mygojekapp.injection

import android.app.Application
import com.gojek.mygojekapp.WeatherApplication
import com.gojek.mygojekapp.injection.module.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    ApplicationModule::class,
    UiModule::class,
    RemoteModule::class,
    DataModule::class,
    DomainModule::class,
    PresentationModule::class
))
interface ApplicationComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application):Builder
        fun build():ApplicationComponent
    }

    fun inject(aplication:WeatherApplication)
}