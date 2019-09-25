package com.techticz.app.injection.module

import com.techticz.data.datamanager.FacilityDataManager
import com.techticz.data.datamanager.FacilityDataManagerImpl
import com.techticz.domain.executor.JobExecutor
import com.techticz.domain.executor.ThreadExecutor
import dagger.Binds
import dagger.Module


@Module
abstract class DataModule{
    @Binds
    abstract fun bindFacilityDataManager(dataManagerImpl: FacilityDataManagerImpl): FacilityDataManager


    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}