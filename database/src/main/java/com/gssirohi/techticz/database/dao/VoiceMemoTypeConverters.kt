package com.gssirohi.techticz.database.dao

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gssirohi.techticz.database.model.DB_MemoAudio

class VoiceMemoTypeConverters{
    @TypeConverter
    fun audioMemoToString(model:DB_MemoAudio?):String{
        return Gson().toJson(model)
    }

    @TypeConverter
    fun stringToAudioMemo(dbvalue:String?):DB_MemoAudio{
        val type = object : TypeToken<DB_MemoAudio>() {
        }.type
        return Gson().fromJson(dbvalue,type)
    }
}