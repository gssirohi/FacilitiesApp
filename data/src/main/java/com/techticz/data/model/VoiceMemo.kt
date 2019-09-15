package com.techticz.data.model

data class VoiceMemo(val id:Int,
                     var voiceBookId:Long, val title:String, val desc:String, val audio:MemoAudio, val timestamp:Long)

data class MemoAudio(val fileName:String, val localPath:String,val remotePath:String, val desc:String)