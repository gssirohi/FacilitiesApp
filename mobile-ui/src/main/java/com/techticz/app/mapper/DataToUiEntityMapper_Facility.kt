package com.techticz.ui.mapper


import com.techticz.data.model.Facility
import com.techticz.data.model.Option
import com.techticz.ui.model.Ui_Facility
import com.techticz.ui.model.Ui_Option

import javax.inject.Inject

open class DataToUiEntityMapper_Facility @Inject constructor():DataToUiEntityMapper<Facility, Ui_Facility> {
    override fun mapFromData(dataEntity: Facility): Ui_Facility {
        var uiEntity = Ui_Facility(
            dataEntity.facility_id,
            dataEntity.name,
            null,
            false,
            dataEntity.options.map { r-> Ui_Option(r.name,r.icon,r.id,false,false, mutableListOf(),
                mutableListOf()) }
        )

        return uiEntity
    }
}