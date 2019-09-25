package com.techticz.app.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Environment
import android.view.View
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import android.R.attr.factor
import android.R.color
import android.graphics.Color
import com.techticz.app.R


class AppUtils{
    companion object {
        fun convertFromYYYYMMDDToDate(dateString: String): Date? {
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                return dateFormat.parse(dateString)
            } catch (e:Exception){
                Timber.e("Invalid date format!")
                return null
            }
        }

        fun darkerLighterColor(color:Int,factor:Float):Int{
            val a = Color.alpha(color)
            val r = Color.red(color)
            val g = Color.green(color)
            val b = Color.blue(color)

            return Color.argb(
                a,
                Math.max((r * factor).toInt(), 0),
                Math.max((g * factor).toInt(), 0),
                Math.max((b * factor).toInt(), 0)
            )
        }

        fun getDisplayDate(cal:Calendar):String{
            var display =""+
            cal.get(Calendar.DAY_OF_MONTH)+" "+
            cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault())+" "+
            cal.get(Calendar.YEAR)+" | "+
            cal.get(Calendar.HOUR_OF_DAY)+":"+
            cal.get(Calendar.MINUTE)
            return display
        }

        fun getDayNameForPosition(position: Int): String? {
            var cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR,position+1)
            return cal.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG,Locale.getDefault())
        }

        fun readJsonFromFile(context: Context, fileName:String):String{

            val iStream = context.getAssets().open(fileName)
            val textBuilder = StringBuilder()
            BufferedReader(InputStreamReader(iStream)).use { reader ->
                var c = 0
                while (c != -1) {
                    c = reader.read()
                    textBuilder.append(c.toChar())
                }
            }
            var text = textBuilder.toString().replace("\uFFFF","")
            return text
        }


        fun getTimeStamp(): Long {
            return Calendar.getInstance().timeInMillis
        }

        fun getIconRes(name:String):Int{
            return when(name){
                "apartment"-> R.drawable.apartment
                "boat"->R.drawable.boat
                "condo"->R.drawable.condo
                "garage"->R.drawable.garage
                "garden"->R.drawable.garden
                "land"->R.drawable.land
                "no-room"->R.drawable.no_room
                "rooms"->R.drawable.rooms
                "swimming"->R.drawable.swimming
                else->R.drawable.no_room
            }
        }
    }

}