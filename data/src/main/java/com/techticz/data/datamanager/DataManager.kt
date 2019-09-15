package com.techticz.data.datamanager

import com.techticz.data.model.VoiceMemo
import com.techticz.database.model.VoiceBook
import io.reactivex.Flowable

interface DataManager {

    fun getVoiceBooks(): Flowable<List<VoiceBook>>
    fun getVoiceMemos(bookId:Long): Flowable<List<VoiceMemo>>
    fun createVoiceMemo(voiceMemo: VoiceMemo): Flowable<Long>
    fun createVoiceBook(voiceBook: VoiceBook): Flowable<Long>
    fun recentVoiceMemos(): Flowable<List<VoiceMemo>>
    fun getVoiceBook(id: Long?): Flowable<VoiceBook>
    fun deleteMemo(voiceMemo:VoiceMemo):Flowable<Int>
}