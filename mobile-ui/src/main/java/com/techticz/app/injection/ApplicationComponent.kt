package com.techticz.app.injection

import android.app.Application
import com.techticz.app.TechticzApplication
import com.techticz.app.injection.module.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    ApplicationModule::class,
    UiModule::class,
    RemoteModule::class,
    DatabaseModule::class,
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

    fun inject(aplication:TechticzApplication)
}