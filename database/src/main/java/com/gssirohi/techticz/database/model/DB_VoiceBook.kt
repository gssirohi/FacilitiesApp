package com.techticz.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DB_VoiceBook(@PrimaryKey(autoGenerate = false) val id:Long, val title:String, val color:Int, var memoCount:Int)
