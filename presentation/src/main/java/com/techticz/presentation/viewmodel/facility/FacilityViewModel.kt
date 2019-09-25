package com.techticz.presentation.viewmodel.facility

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techticz.data.model.FacilityData
import com.techticz.domain.interactor.facility.GetFacilitiesUseCase
import com.techticz.domain.model.FacilityDataRequest
import io.reactivex.subscribers.DisposableSubscriber
import org.buffer.android.boilerplate.presentation.data.Resource
import org.buffer.android.boilerplate.presentation.data.ResourceState
import java.util.*

import javax.inject.Inject

class FacilityViewModel @Inject internal constructor(
    private val USECASE_GET_FACILITIES: GetFacilitiesUseCase
): ViewModel(){

    private val facilitiesLiveData:MutableLiveData<Resource<FacilityData>> = MutableLiveData()

    init {

    }

    override fun onCleared() {
        USECASE_GET_FACILITIES.dispose()
        super.onCleared()
    }

    fun fetchFacilities(req: FacilityDataRequest) {
        facilitiesLiveData.postValue(Resource(ResourceState.LOADING,null,null))
        USECASE_GET_FACILITIES.execute(FacilitiesSubscriber(), req)
    }


    fun getFacilitiesLiveData():MutableLiveData<Resource<FacilityData>>{
        return facilitiesLiveData
    }


    private inner class FacilitiesSubscriber:DisposableSubscriber<FacilityData>() {
        override fun onComplete() {}

        override fun onNext(facilityData: FacilityData?) {
            if(facilityData == null || facilityData.isOlderThanOneDay()) {
                Log.i("PRESENTAION","Older facilities received: requesting latest")
                fetchFacilities(FacilityDataRequest(true))//bring latest data from remote
            } else {
                Log.i("PRESENTAION","facilities received : latest not required")
                facilitiesLiveData.postValue(Resource(ResourceState.SUCCESS, facilityData!!, null))
            }
        }

        override fun onError(t: Throwable?) {
            if(t!!.message!!.contains("empty result set")){
                Log.i("PRESENTAION","Empty facilities data received: requesting latest")
                fetchFacilities(FacilityDataRequest(true))//bring latest data from remote
            } else {
                Log.i("PRESENTAION", "Error occured while fetching facilities")
                facilitiesLiveData.postValue(Resource(ResourceState.ERROR, null, t?.message))
            }
        }

    }

}

private fun FacilityData.isOlderThanOneDay(): Boolean {
    var today = Calendar.getInstance()
    var MILIS_IN_DAY = 1000*60*60*24
    var MILIS_IN_MINUTE = 1000*60 // For testing
    return Math.abs(this.syncTime-today.timeInMillis) >= MILIS_IN_MINUTE

}
