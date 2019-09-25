package com.techticz.ui.mapper


import com.techticz.data.model.Facility
import com.techticz.data.model.FacilityData
import com.techticz.remote.model.FacilityResponse
import com.techticz.remote.model.Remote_Exclusion
import com.techticz.remote.model.Remote_Facility
import com.techticz.ui.model.Ui_FacilityData
import com.techticz.ui.model.Ui_Option
import java.util.*

import javax.inject.Inject

open class DataToUiEntityMapper_FacilityData @Inject constructor(val facilityMapper:DataToUiEntityMapper_Facility)
    :DataToUiEntityMapper<FacilityData, Ui_FacilityData> {
    override fun mapFromData(remoteEntity: FacilityData): Ui_FacilityData {
        var dataEntity = Ui_FacilityData(
            remoteEntity.facilities.map { r: Facility ->facilityMapper.mapFromData(r) },
            remoteEntity.exclusions
        )
        dataEntity.exclusions.forEach {exclusion->
            exclusion.exclusiveItems.forEach {exclusionItem->
                var option  = dataEntity.retriveOption(exclusionItem)
                option!!.addExclusionInfo(exclusion.exclusiveItems)
            }
        }
        return dataEntity
    }
}