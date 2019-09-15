package com.techticz.remote

import com.techticz.database.model.Remote_VoiceBook
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface VoiceBookService {

    @GET(RemoteConstants.SUFFIX_VOICE_BOOKS)
    fun getForecast(
        @Query("key") key:String
    ):Flowable<List<Remote_VoiceBook>>


}