package com.gssirohi.techticz.database

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gssirohi.techticz.database.dao.ExclusionTypeConverters
import com.gssirohi.techticz.database.dao.FacilityTypeConverters
import com.gssirohi.techticz.database.model.FacilityRecord
import com.techticz.app.db.dao.FacilityRecordDao
import io.reactivex.subscribers.DisposableSubscriber
import java.util.*
import java.util.concurrent.Executors

@Database(entities = [FacilityRecord::class], version = 1)
@TypeConverters(FacilityTypeConverters::class,ExclusionTypeConverters::class)
abstract class FacilityAppDatabase : RoomDatabase() {
    open abstract fun facilityRecordDao(): FacilityRecordDao
    companion object {
        var INSTANCE:FacilityAppDatabase? = null
        fun getFacilityAppDataBase(application: Application): FacilityAppDatabase {
            if(INSTANCE != null){
                return INSTANCE!!
            } else {
                INSTANCE = Room.databaseBuilder(application, FacilityAppDatabase::class.java, "FacilityAppDB")
                    .build()
                return INSTANCE!!
            }

        }

    }


}