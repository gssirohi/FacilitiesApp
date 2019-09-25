package com.techticz.app.injection.component

import android.app.Application
import com.techticz.data.repository.RemoteRepository
import com.techticz.data.datamanager.DataManager
import com.techticz.domain.executor.PostExecutionThread
import com.techticz.app.injection.ApplicationComponent
import com.techticz.app.injection.module.*
import com.techticz.app.test.TestApplication
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

    fun weatherDataManager(): DataManager

    fun remoreRepository():RemoteRepository

    fun postExecutionThread(): PostExecutionThread

    fun inject(application: TestApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): TestApplicationComponent.Builder

        fun build(): TestApplicationComponent
    }

}