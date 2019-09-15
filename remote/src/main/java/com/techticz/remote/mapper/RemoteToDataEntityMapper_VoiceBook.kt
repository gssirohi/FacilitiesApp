package com.techticz.remote.mapper


import com.techticz.database.model.Remote_VoiceBook
import com.techticz.database.model.VoiceBook

import javax.inject.Inject

open class RemoteToDataEntityMapper_VoiceBook @Inject constructor():RemoteToDataEntityMapper<Remote_VoiceBook,VoiceBook> {
    override fun mapFromRemote(remoteEntity: Remote_VoiceBook): VoiceBook {
        var dataEntity = VoiceBook(
            remoteEntity.id,
            remoteEntity.title,
            remoteEntity.color,
            remoteEntity.memoCount

        )

        return dataEntity
    }
}