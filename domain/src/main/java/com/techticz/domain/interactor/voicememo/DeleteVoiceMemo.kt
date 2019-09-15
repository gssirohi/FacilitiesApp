package com.techticz.domain.interactor.voicememo

import com.techticz.database.model.VoiceBook
import com.techticz.data.datamanager.DataManager
import com.techticz.data.model.VoiceMemo
import com.techticz.domain.executor.PostExecutionThread
import com.techticz.domain.executor.ThreadExecutor
import com.techticz.domain.interactor.FlowableUseCase
import com.techticz.domain.model.VoiceBooksRequest
import com.techticz.domain.model.VoiceMemosRequest
import io.reactivex.Flowable

import javax.inject.Inject

open class DeleteVoiceMemo @Inject constructor(private val dataManager: DataManager,
                                               threadExecutor: ThreadExecutor,
                                               postThreadExecutor: PostExecutionThread
) :
FlowableUseCase <Int,VoiceMemo>(threadExecutor,postThreadExecutor){

    public override fun buildUseCaseObservable(params: VoiceMemo?): Flowable<Int> {
        return dataManager.deleteMemo(params!!)
    }

}