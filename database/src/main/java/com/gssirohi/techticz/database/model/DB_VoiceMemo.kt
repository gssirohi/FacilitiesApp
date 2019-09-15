package com.gssirohi.techticz.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gssirohi.techticz.database.dao.VoiceMemoTypeConverters

@Entity
data class DB_VoiceMemo(@PrimaryKey(autoGenerate = true) val id:Int, val voiceBookId:Long, val title:String, val transcript:String,

                        /*@TypeConverters(VoiceMemoTypeConverters::class)*/
                        @Embedded
                        val audio:DB_MemoAudio,

                        val timestamp:Long)

data class DB_MemoAudio(val fileName:String, val localPath:String, val remotePath:String, val desc:String)