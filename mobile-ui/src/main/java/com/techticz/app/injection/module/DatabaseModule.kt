package com.techticz.app.injection.module

import android.content.Context
import com.gssirohi.techticz.database.FacilityAppDatabase
import com.gssirohi.techticz.database.FacilityDatabaseRepositoryImpl
import com.techticz.app.TechticzApplication
import com.techticz.data.repository.FacilityDatabaseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class DatabaseModule {
    //In order to provide dependencies statically
    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideFacilityAppDatabase(context: Context):FacilityAppDatabase{
            val db = FacilityAppDatabase.getFacilityAppDataBase(context as TechticzApplication)
            return db
        }
    }

    @Binds
    abstract fun bindFacilityDatabaseRepositoryImpl(databaseRepositoryImpl: FacilityDatabaseRepositoryImpl): FacilityDatabaseRepository
}