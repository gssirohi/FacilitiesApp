package com.techticz.app.db.dao

import androidx.room.*

import com.gssirohi.techticz.database.dao.BaseDao
import com.gssirohi.techticz.database.model.FacilityRecord

@Dao
abstract class FacilityRecordDao: BaseDao<FacilityRecord>()