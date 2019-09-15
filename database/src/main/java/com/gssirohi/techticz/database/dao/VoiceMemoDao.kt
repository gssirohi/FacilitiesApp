package com.techticz.app.db.dao

import androidx.room.*

import com.gssirohi.techticz.database.dao.BaseDao
import com.gssirohi.techticz.database.model.DB_VoiceMemo
import com.techticz.database.model.DB_VoiceBook

@Dao
abstract class VoiceMemoDao: BaseDao<DB_VoiceMemo>()