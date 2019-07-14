package com.gojek.mygojekapp.utils

import android.content.Context
import android.os.Environment
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

        fun formateInDegreeCelcius(temp_c: Float?): CharSequence? {
            if(temp_c == null) return ""
            return ""+temp_c?.roundToInt()+"\u00B0"
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
    }


}