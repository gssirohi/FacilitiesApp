package com.gssirohi.techticz.database.dao

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techticz.database.model.DB_Exclusion
import com.techticz.database.model.DB_Facility

class FacilityTypeConverters{
    @TypeConverter
    fun facilitiesToString(model:List<DB_Facility>):String{
        if(model == null){
            return ""
        }
        return Gson().toJson(model)
    }

    @TypeConverter
    fun stringToFacilities(dbvalue:String?):List<DB_Facility>{
        if(dbvalue.equals("")){
            return mutableListOf()
        }
        val type = object : TypeToken<List<DB_Facility>>() {
        }.type
        return Gson().fromJson<List<DB_Facility>>(dbvalue,type)
    }

}