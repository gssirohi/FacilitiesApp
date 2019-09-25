package com.techticz.remote

import com.techticz.remote.model.FacilityResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface FacilityRemoteService {

    @GET(RemoteConstants.SUFFIX_FACILITIES)
    fun getFacilities():Flowable<FacilityResponse>


}