package com.techticz.remote.mapper


import com.techticz.data.model.Exclusion
import com.techticz.data.model.Facility
import com.techticz.data.model.FacilityData
import com.techticz.data.model.Option
import com.techticz.remote.model.FacilityResponse
import com.techticz.remote.model.Remote_Exclusion
import com.techticz.remote.model.Remote_ExclusiveItem
import com.techticz.remote.model.Remote_Facility
import java.util.*

import javax.inject.Inject

open class RemoteToDataEntityMapper_FacilityData @Inject constructor(val facilityMapper:RemoteToDataEntityMapper_Facility,
                                                                     val exclusionMapper:RemoteToDataEntityMapper_Exclusion)
    :RemoteToDataEntityMapper<FacilityResponse, FacilityData> {
    override fun mapFromRemote(remoteEntity: FacilityResponse): FacilityData {
        var exclusionList = mutableListOf<Exclusion>()
        remoteEntity.exclusions.forEach {
            var exclusion = exclusionMapper.mapFromRemote(it)
            exclusionList.add(exclusion)
        }
        var dataEntity = FacilityData(
            Calendar.getInstance().timeInMillis,
            remoteEntity.facilities.map { r:Remote_Facility->facilityMapper.mapFromRemote(r) },
            exclusionList
        )

        return dataEntity
    }
}