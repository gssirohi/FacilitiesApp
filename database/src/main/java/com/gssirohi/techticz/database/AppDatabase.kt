package com.gssirohi.techticz.database

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gssirohi.techticz.database.model.DB_VoiceMemo
import com.gssirohi.techticz.database.model.DB_MemoAudio
import com.techticz.app.db.dao.VoiceBookDao
import com.techticz.app.db.dao.VoiceMemoDao
import com.techticz.data.model.VoiceMemo

import com.techticz.database.model.DB_VoiceBook
import io.reactivex.subscribers.DisposableSubscriber
import java.util.*
import java.util.concurrent.Executors

@Database(entities = [DB_VoiceBook::class,DB_VoiceMemo::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    open abstract fun voiceBookDao(): VoiceBookDao
    open abstract fun voiceMemoDao():VoiceMemoDao
    companion object {
        var INSTANCE:AppDatabase? = null
        class VoiceBooksSubscriber: DisposableSubscriber<List<Long>>() {
            override fun onComplete() {
                Log.d("DB","Initial Items inserted into DB")
            }

            override fun onNext(t: List<Long>?) {
                if(t != null) {
                    Log.d("DB","Inserting Initial items into DB")
                } else {
                    Log.d("DB","Error while inserting items into DB")
                }
            }

            override fun onError(t: Throwable?) {
                Log.d("DB","Error while inserting initial items into DB")
            }

        }
        fun getAppDataBase(application: Application): AppDatabase {
            if(INSTANCE != null){
                return INSTANCE!!
            } else {
                INSTANCE = Room.databaseBuilder(application, AppDatabase::class.java, "VoiceBookDB")
                    .addCallback(object: Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Executors.newSingleThreadScheduledExecutor().execute(Runnable {
                                var list = mutableListOf<DB_VoiceBook>()
                                list.add(DB_VoiceBook(1L,"ToDo Book", Color.parseColor("#ef5350"),0))
                                list.add(DB_VoiceBook(2L,"Business Ideas", Color.parseColor("#1e88e5"),0))
                                list.add(DB_VoiceBook(3L,"Project Tasks", Color.parseColor("#757575"),0))
                                var res = INSTANCE?.voiceBookDao()?.insert(list)
                                res!!.toFlowable().subscribeWith(AppDatabase.Companion.VoiceBooksSubscriber())

                            })
                        }
                    }).build()
                return INSTANCE!!
            }

        }

    }


}