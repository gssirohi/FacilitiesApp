package com.techticz.app.utils

import android.content.Context
import android.content.SharedPreferences

class AppPref {
    companion object{

        fun getAppPref(context:Context): SharedPreferences? {
            return context.getSharedPreferences("appPref",Context.MODE_PRIVATE)
        }


    }
}