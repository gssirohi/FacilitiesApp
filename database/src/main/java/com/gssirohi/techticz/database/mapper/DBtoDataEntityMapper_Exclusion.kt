package com.techticz.database.mapper

import com.techticz.data.model.*
import com.techticz.database.model.DB_Exclusion
import com.techticz.database.model.DB_ExclusiveItem

import javax.inject.Inject

open class DBtoDataEntityMapper_Exclusion @Inject constructor():DBtoDataEntityMapper<DB_Exclusion, Exclusion> {
    override fun mapToDB(dataEntity: Exclusion): DB_Exclusion {
        var dataEntity = DB_Exclusion(
            dataEntity.exclusiveItems.map { r-> DB_ExclusiveItem(r.facility_id,r.options_id) }
        )

        return dataEntity
    }

    override fun mapFromDB(dbEntity: DB_Exclusion): Exclusion {
        var dataEntity = Exclusion(
            dbEntity.exclusiveItems.map { r-> ExclusiveItem(r.facility_id,r.options_id) }
        )

        return dataEntity
    }
}