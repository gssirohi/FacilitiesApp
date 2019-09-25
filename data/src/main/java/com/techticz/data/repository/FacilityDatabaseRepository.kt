package com.techticz.data.repository

import com.techticz.data.model.FacilityData
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface FacilityDatabaseRepository {
    fun getFacilityData(): Single<FacilityData>
    fun insertFacilityData(facilityData: FacilityData?):Single<Long>
}