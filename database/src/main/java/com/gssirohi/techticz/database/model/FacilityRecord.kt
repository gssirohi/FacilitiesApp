package com.gssirohi.techticz.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gssirohi.techticz.database.dao.ExclusionTypeConverters
import com.gssirohi.techticz.database.dao.FacilityTypeConverters
import com.techticz.database.model.DB_Exclusion
import com.techticz.database.model.DB_Facility


@Entity
class FacilityRecord(
    @PrimaryKey
    val id:Int,
    val syncTime:Long,
    @TypeConverters(FacilityTypeConverters::class)
    var facilities:List<DB_Facility>,
    @TypeConverters(ExclusionTypeConverters::class)
    var exclusions:List<DB_Exclusion>)
