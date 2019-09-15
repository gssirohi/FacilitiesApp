package com.techticz.database.mapper

import com.gssirohi.techticz.database.model.DB_VoiceMemo
import com.gssirohi.techticz.database.model.DB_MemoAudio
import com.techticz.data.model.MemoAudio

import com.techticz.data.model.VoiceMemo

import javax.inject.Inject

open class DBtoDataEntityMapper_VoiceMemo @Inject constructor():DBtoDataEntityMapper<DB_VoiceMemo, VoiceMemo> {
    override fun mapToDB(dataEntity: VoiceMemo): DB_VoiceMemo {
        var audio = DB_MemoAudio(dataEntity.audio.fileName,dataEntity.audio.localPath,dataEntity.audio.remotePath,dataEntity.audio.desc)
        var dbEntity  = DB_VoiceMemo(
            dataEntity.id,
            dataEntity.voiceBookId,
            dataEntity.title,
            dataEntity.desc,
            audio,
            dataEntity.timestamp
        )
        return dbEntity
    }

    override fun mapFromDB(dbEntity: DB_VoiceMemo): VoiceMemo {
        var audio = MemoAudio(dbEntity.audio.fileName,dbEntity.audio.localPath,dbEntity.audio.remotePath,dbEntity.audio.desc)
        var dataEntity  = VoiceMemo(
            dbEntity.id,
            dbEntity.voiceBookId,
            dbEntity.title,
            dbEntity.transcript,
            audio,
            dbEntity.timestamp
        )
        return dataEntity
    }
}