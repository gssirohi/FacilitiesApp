package com.techticz.data.repository

import com.techticz.data.model.VoiceMemo
import com.techticz.database.model.VoiceBook
import io.reactivex.Maybe
import io.reactivex.Single

interface DatabaseRepository {
    fun getVoiceBooks(): Maybe<List<VoiceBook>>
    fun getVoiceMemos(bookId:Long): Maybe<List<VoiceMemo>>
    fun createVoiceMemo(voiceMemo: VoiceMemo): Single<Long>
    fun createVoiceBook(voiceBook: VoiceBook): Single<Long>
    fun deleteVoiceMemo(voiceMemo: VoiceMemo): Single<Int>
    fun recentVoiceMemos(): Maybe<List<VoiceMemo>>
    fun getVoiceBook(id: Long?): Single<VoiceBook>
}