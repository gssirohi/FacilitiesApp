package com.techticz.database.mapper

import com.techticz.database.model.DB_VoiceBook
import com.techticz.database.model.VoiceBook

import javax.inject.Inject

open class DBtoDataEntityMapper_VoiceBook @Inject constructor():DBtoDataEntityMapper<DB_VoiceBook,VoiceBook> {
    override fun mapToDB(dataEntity: VoiceBook): DB_VoiceBook {
        var dbEntity  = DB_VoiceBook(
            dataEntity.id,
            dataEntity.title,
            dataEntity.color,
            dataEntity.memoCount
        )
        return dbEntity
    }

    override fun mapFromDB(dbEntity: DB_VoiceBook): VoiceBook {
        var dataEntity  = VoiceBook(
            dbEntity.id,
            dbEntity.title,
            dbEntity.color,
            dbEntity.memoCount
        )
        return dataEntity
    }
}