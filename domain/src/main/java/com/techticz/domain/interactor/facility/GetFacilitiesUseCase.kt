package com.techticz.domain.interactor.facility



import com.techticz.data.datamanager.FacilityDataManager
import com.techticz.data.model.FacilityData
import com.techticz.domain.executor.PostExecutionThread
import com.techticz.domain.executor.ThreadExecutor
import com.techticz.domain.interactor.FlowableUseCase
import com.techticz.domain.model.FacilityDataRequest

import io.reactivex.Flowable

import javax.inject.Inject

open class GetFacilitiesUseCase @Inject constructor(private val dataManager: FacilityDataManager,
                                                    threadExecutor: ThreadExecutor,
                                                    postThreadExecutor: PostExecutionThread
) :
FlowableUseCase <FacilityData,FacilityDataRequest>(threadExecutor,postThreadExecutor){

    public override fun buildUseCaseObservable(params: FacilityDataRequest?): Flowable<FacilityData> {
        return dataManager.getFacilityData(params!!.latest)
    }

}