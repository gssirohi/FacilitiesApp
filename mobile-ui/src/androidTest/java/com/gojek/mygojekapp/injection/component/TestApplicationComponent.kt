package com.gojek.mygojekapp.injection.component

import android.app.Application
import com.gojek.domain.datamanager.WeatherDataManager
import com.gojek.domain.executor.PostExecutionThread
import com.gojek.mygojekapp.injection.ApplicationComponent
import com.gojek.mygojekapp.injection.module.*
import com.gojek.mygojekapp.test.TestApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    TestApplicationModule::class,
    AndroidSupportInjectionModule::class,
    TestRemoteModule::class,
    TestDataModule::class,
    PresentationModule::class,
    UiModule::class))
interface TestApplicationComponent : ApplicationComponent {

    fun weatherDataManager(): WeatherDataManager

    fun postExecutionThread(): PostExecutionThread

    fun inject(application: TestApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): TestApplicationComponent.Builder

        fun build(): TestApplicationComponent
    }

}