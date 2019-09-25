package com.techticz.database.mapper

import com.gssirohi.techticz.database.model.FacilityRecord
import com.techticz.data.model.*

import com.techticz.database.model.DB_Exclusion
import com.techticz.database.model.DB_Facility

import javax.inject.Inject

open class DBtoDataEntityMapper_FacilityData @Inject constructor(
    val facilityMapper:DBtoDataEntityMapper_Facility,
    val exclusionMapper:DBtoDataEntityMapper_Exclusion
):DBtoDataEntityMapper<FacilityRecord, FacilityData> {
    override fun mapToDB(dataEntity: FacilityData): FacilityRecord {
        var dataEntity = FacilityRecord(
            1,
            dataEntity.syncTime,
            dataEntity.facilities.map { r: Facility ->facilityMapper.mapToDB(r) },
            dataEntity.exclusions.map{ r: Exclusion ->exclusionMapper.mapToDB(r)}
        )

        return dataEntity
    }

    override fun mapFromDB(dbEntity: FacilityRecord): FacilityData {
        var dataEntity = FacilityData(
            dbEntity.syncTime,
            dbEntity.facilities.map { r:DB_Facility->facilityMapper.mapFromDB(r) },
            dbEntity.exclusions.map{ r: DB_Exclusion ->exclusionMapper.mapFromDB(r)}
        )

        return dataEntity
    }
}