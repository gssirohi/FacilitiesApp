package com.techticz.domain.interactor.voicebook

import com.techticz.database.model.VoiceBook
import com.techticz.data.datamanager.DataManager
import com.techticz.domain.executor.PostExecutionThread
import com.techticz.domain.executor.ThreadExecutor
import com.techticz.domain.interactor.FlowableUseCase
import com.techticz.domain.model.VoiceBooksRequest
import io.reactivex.Flowable

import javax.inject.Inject

open class GetVoiceBooks @Inject constructor(private val dataManager: DataManager,
                                             threadExecutor: ThreadExecutor,
                                             postThreadExecutor: PostExecutionThread
) :
FlowableUseCase <List<VoiceBook>,VoiceBooksRequest>(threadExecutor,postThreadExecutor){

    public override fun buildUseCaseObservable(params: VoiceBooksRequest?): Flowable<List<VoiceBook>> {
        return dataManager.getVoiceBooks()
    }

}