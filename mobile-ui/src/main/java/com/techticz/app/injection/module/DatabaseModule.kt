package com.techticz.app.injection.module

import android.content.Context
import com.gssirohi.techticz.database.AppDatabase
import com.gssirohi.techticz.database.DatabaseRepositoryImpl
import com.techticz.app.TechticzApplication
import com.techticz.data.repository.DatabaseRepository
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
        fun provideAppDatabase(context: Context):AppDatabase{
            val db = AppDatabase.getAppDataBase(context as TechticzApplication)
            return db
        }
    }
    @Binds
    abstract fun bindDatabaseRepositoryImpl(remoteRepositoryImpl: DatabaseRepositoryImpl):DatabaseRepository
}