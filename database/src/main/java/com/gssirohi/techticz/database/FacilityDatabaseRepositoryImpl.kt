package com.gssirohi.techticz.database

import android.util.Log
import com.techticz.data.model.FacilityData
import com.techticz.data.repository.FacilityDatabaseRepository
import com.techticz.database.mapper.DBtoDataEntityMapper_FacilityData
import io.reactivex.Single

import javax.inject.Inject

class FacilityDatabaseRepositoryImpl@Inject constructor(
    val appDb: FacilityAppDatabase,
    val facilityDataMapper: DBtoDataEntityMapper_FacilityData

):FacilityDatabaseRepository{
    override fun insertFacilityData(facilityData: FacilityData?): Single<Long> {
        Log.i("DB","inserting to DB")
        return appDb.facilityRecordDao().upsert(facilityDataMapper.mapToDB(facilityData!!))
    }

    override fun getFacilityData(): Single<FacilityData> {
        Log.i("DB","getting from DB")
        appDb.facilityRecordDao().findAll().toFlowable()
        return appDb.facilityRecordDao().findByProperty("id","1").map{facilityDataMapper.mapFromDB(it)}
    }
}