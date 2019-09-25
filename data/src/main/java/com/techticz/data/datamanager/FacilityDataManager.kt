package com.techticz.data.datamanager

import com.techticz.data.model.FacilityData
import io.reactivex.Flowable

interface FacilityDataManager {
    fun getFacilityData(latest: Boolean): Flowable<FacilityData>
}