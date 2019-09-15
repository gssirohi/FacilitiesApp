package com.techticz.data.repository

import com.techticz.database.model.VoiceBook
import io.reactivex.Flowable
import io.reactivex.Maybe

interface RemoteRepository {
    fun getVoiceBooks(): Flowable<List<VoiceBook>>
}