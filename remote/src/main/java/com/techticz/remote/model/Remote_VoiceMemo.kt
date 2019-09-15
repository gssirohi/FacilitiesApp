package com.techticz.data.model

data class Remote_VoiceMemo(val id:Int,val voiceBookId:Int,val title:String,val desc:String,val audio:Remote_MemoAudio,val timestamp:Long)

data class Remote_MemoAudio(val fileName:String, val localPath:String,val remotePath:String, val desc:String)