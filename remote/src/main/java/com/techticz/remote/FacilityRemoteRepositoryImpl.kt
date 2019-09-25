package com.techticz.remote


import com.techticz.data.model.FacilityData
import com.techticz.data.repository.FacilityRemoteRepository

import com.techticz.remote.mapper.RemoteToDataEntityMapper_Facility
import com.techticz.remote.mapper.RemoteToDataEntityMapper_FacilityData

import io.reactivex.Flowable
import sun.rmi.runtime.Log
import javax.inject.Inject

class FacilityRemoteRepositoryImpl @Inject constructor(val facilityService: FacilityRemoteService, val entityMapper: RemoteToDataEntityMapper_FacilityData):FacilityRemoteRepository {
    override fun getFacilityData(): Flowable<FacilityData> {
        return facilityService.getFacilities().map { entityMapper.mapFromRemote(it) }
    }
}