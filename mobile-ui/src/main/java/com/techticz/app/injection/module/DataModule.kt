package com.techticz.app.injection.module

import com.techticz.data.datamanager.DataManagerImpl
import com.techticz.data.datamanager.DataManager
import com.techticz.domain.executor.JobExecutor
import com.techticz.domain.executor.ThreadExecutor
import dagger.Binds
import dagger.Module


@Module
abstract class DataModule{
    @Binds
    abstract fun bindDataManager(dataManagerImpl: DataManagerImpl): DataManager


    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}