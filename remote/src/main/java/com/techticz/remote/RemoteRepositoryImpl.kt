package com.techticz.remote


import com.techticz.data.repository.RemoteRepository
import com.techticz.database.model.VoiceBook
import com.techticz.remote.mapper.RemoteToDataEntityMapper_VoiceBook
import io.reactivex.Flowable
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(val voiceBookService: VoiceBookService, val entityMapper: RemoteToDataEntityMapper_VoiceBook):RemoteRepository {
    override fun getVoiceBooks(): Flowable<List<VoiceBook>> {
        return voiceBookService.getForecast(RemoteConstants.WEATHER_API_KEY).map{it}
            .map{
                val entities = mutableListOf<VoiceBook>()
                it.forEach{entities.add(entityMapper.mapFromRemote(it))}
                entities
            }
    }


    /* override fun getVoiceBooks(): Flowable<List<VoiceBook>> {
         return dbRepo.getVoiceBooks().toFlowable().map { it }
             .map {
                 val entities = mutableListOf<VoiceBook>()
                 it.forEach { entities.add(voicebookMapper.mapFromDataToDomain(it)) }
                 entities
             }
     }*/
}