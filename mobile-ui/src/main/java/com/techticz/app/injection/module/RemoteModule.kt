package com.techticz.app.injection.module

import com.techticz.app.BuildConfig
import com.techticz.data.repository.FacilityRemoteRepository
import com.techticz.remote.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {
    //In order to provide dependencies statically
    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideFacilityService():FacilityRemoteService{
            return FacilityRemoteServiceFactory.makeFacilityRemoteService(BuildConfig.DEBUG)
        }
    }


    @Binds
    abstract fun bindFacilityRemoteRepositoryImpl(remoteRepositoryImpl:FacilityRemoteRepositoryImpl): FacilityRemoteRepository
}