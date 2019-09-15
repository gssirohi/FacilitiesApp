package com.techticz.app.utils

import android.content.Context
import android.content.SharedPreferences

class AppPref {
    companion object{

        fun getAppPref(context:Context): SharedPreferences? {
            return context.getSharedPreferences("appPref",Context.MODE_PRIVATE)
        }

        fun getDefaultBookId(context: Context): Long {
            var editor = getAppPref(context)?.edit()
            return getAppPref(context)!!.getLong("default_book_id",1L)

        }

        fun setDefaultBookId(context: Context, id: Long){
            var editor = getAppPref(context)?.edit()
            editor!!.putLong("default_book_id",id)
            editor.commit()
            editor.apply()

        }
    }
}