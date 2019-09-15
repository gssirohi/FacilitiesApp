package com.gssirohi.techticz.database

import android.util.Log
import com.techticz.data.model.VoiceMemo
import com.techticz.data.repository.DatabaseRepository
import com.techticz.database.mapper.DBtoDataEntityMapper_VoiceBook
import com.techticz.database.mapper.DBtoDataEntityMapper_VoiceMemo
import com.techticz.database.model.DB_VoiceBook
import com.techticz.database.model.VoiceBook
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

import javax.inject.Inject

class DatabaseRepositoryImpl@Inject constructor(val appDb: AppDatabase,
                                                val voiceBookMapper: DBtoDataEntityMapper_VoiceBook,
                                                val voiceMemoMapper: DBtoDataEntityMapper_VoiceMemo

):DatabaseRepository{
    override fun getVoiceBook(id: Long?): Single<VoiceBook> {
        return appDb.voiceBookDao().findByProperty("id",""+id)
            .map { voiceBookMapper.mapFromDB(it) }
    }


    override fun createVoiceBook(voiceBook: VoiceBook): Single<Long> {
        return appDb.voiceBookDao().insert(voiceBookMapper.mapToDB(voiceBook))
    }

    override fun createVoiceMemo(voiceMemo: VoiceMemo): Single<Long> {
        var id =  appDb.voiceMemoDao().insert(voiceMemoMapper.mapToDB(voiceMemo))
        if(id != null){
            appDb.voiceBookDao().findByProperty("id",""+voiceMemo.voiceBookId)
                .observeOn(Schedulers.single())
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                        t->
                            t.memoCount = t.memoCount+1
                            appDb.voiceBookDao().update(t)

                }
                .doOnError { Log.e("DB","Couldnot update count in Book") }
                .subscribe()
        }
        return id
    }

    override fun deleteVoiceMemo(voiceMemo: VoiceMemo): Single<Int> {
        var res = appDb.voiceMemoDao().delete(voiceMemoMapper.mapToDB(voiceMemo))
        res.observeOn(Schedulers.single())
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                if(it > 0) {
                    appDb.voiceBookDao().findByProperty("id", "" + voiceMemo.voiceBookId)
                        .observeOn(Schedulers.single())
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess { t ->
                            t.memoCount = t.memoCount - 1
                            appDb.voiceBookDao().update(t)

                        }
                        .doOnError { Log.e("DB", "Couldnot update count in Book") }
                        .subscribe()
                }
            }
            .doOnError{

            }
            .subscribe()


        return res
    }

    override fun getVoiceMemos(bookId: Long): Maybe<List<VoiceMemo>> {
        var result = appDb.voiceMemoDao().findAllByProperty("voiceBookId",""+bookId)
        return result.map{it}
            .map{
                val entities = mutableListOf<VoiceMemo>()
                it.forEach{entities.add(voiceMemoMapper.mapFromDB(it))}
                entities
            }
    }

    override fun recentVoiceMemos(): Maybe<List<VoiceMemo>> {
        var result = appDb.voiceMemoDao().orderByProperty("timestamp","ASC")
        return result.map{it}
            .map{
                val entities = mutableListOf<VoiceMemo>()
                it.forEach{entities.add(voiceMemoMapper.mapFromDB(it))}
                entities
            }
    }
    override fun getVoiceBooks(): Maybe<List<VoiceBook>> {
        var result = appDb.voiceBookDao().findAll()
        return result.map{it}
            .map{
                val entities = mutableListOf<VoiceBook>()
                it.forEach{entities.add(voiceBookMapper.mapFromDB(it))}
                entities
            }
    }

}