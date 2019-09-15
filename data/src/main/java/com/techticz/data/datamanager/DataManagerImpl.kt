package com.techticz.data.datamanager


import com.techticz.data.model.VoiceMemo
import com.techticz.data.repository.DatabaseRepository
import com.techticz.data.repository.RemoteRepository
import com.techticz.database.model.VoiceBook

import io.reactivex.Flowable
import javax.inject.Inject

class DataManagerImpl @Inject constructor(val remoteRepo: RemoteRepository,val dbRepo:DatabaseRepository): DataManager {
    override fun deleteMemo(voiceMemo: VoiceMemo): Flowable<Int> {
        return dbRepo.deleteVoiceMemo(voiceMemo).toFlowable()
    }

    override fun recentVoiceMemos(): Flowable<List<VoiceMemo>> {
        return dbRepo.recentVoiceMemos().toFlowable()
    }

    override fun createVoiceBook(voiceBook: VoiceBook): Flowable<Long> {
        return dbRepo.createVoiceBook(voiceBook).toFlowable()
    }

    override fun createVoiceMemo(voiceMemo: VoiceMemo): Flowable<Long> {
        return dbRepo.createVoiceMemo(voiceMemo).toFlowable()
    }

    override fun getVoiceMemos(bookId: Long): Flowable<List<VoiceMemo>> {
        return dbRepo.getVoiceMemos(bookId).toFlowable()
    }

    override fun getVoiceBooks(): Flowable<List<VoiceBook>> {
        return dbRepo.getVoiceBooks().toFlowable()
    }

    override fun getVoiceBook(id:Long?): Flowable<VoiceBook> {
        return dbRepo.getVoiceBook(id).toFlowable()
    }


    /* override fun getVoiceBooks(): Flowable<List<VoiceBook>> {
         return dbRepo.getVoiceBooks().toFlowable().map { it }
             .map {
                 val entities = mutableListOf<VoiceBook>()
                 it.forEach { entities.add(voicebookMapper.mapFromDataToDomain(it)) }
                 entities
             }
     }*/
}