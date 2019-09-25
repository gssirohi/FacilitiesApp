package com.techticz.data.repository

import com.techticz.data.model.FacilityData
import io.reactivex.Flowable

interface FacilityRemoteRepository {
    fun getFacilityData(): Flowable<FacilityData>
}