package com.techticz.database.mapper

import com.techticz.data.model.*
import com.techticz.database.model.DB_Facility
import com.techticz.database.model.DB_Option

import javax.inject.Inject

open class DBtoDataEntityMapper_Facility @Inject constructor():DBtoDataEntityMapper<DB_Facility, Facility> {
    override fun mapToDB(dataEntity: Facility): DB_Facility {
        var dataEntity = DB_Facility(
            dataEntity.facility_id,
            dataEntity.name,
            dataEntity.options.map { r-> DB_Option(r.name,r.icon,r.id) }
        )

        return dataEntity
    }

    override fun mapFromDB(dbEntity: DB_Facility): Facility {
        var dataEntity = Facility(
            dbEntity.facility_id,
            dbEntity.name,
            dbEntity.options.map { r-> Option(r.name,r.icon,r.id) }
        )

        return dataEntity
    }
}