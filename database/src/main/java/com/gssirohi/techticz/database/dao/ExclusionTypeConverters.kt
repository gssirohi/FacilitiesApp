package com.gssirohi.techticz.database.dao

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techticz.database.model.DB_Exclusion
import com.techticz.database.model.DB_Facility

class ExclusionTypeConverters{
    @TypeConverter
    fun exclusionListToString(model:List<DB_Exclusion>?):String{
        return Gson().toJson(model)
    }

    @TypeConverter
    fun stringToExclusionList(dbvalue:String?):List<DB_Exclusion>{
        val type = object : TypeToken<List<DB_Exclusion>>() {
        }.type
        return Gson().fromJson<List<DB_Exclusion>>(dbvalue,type)
    }
}