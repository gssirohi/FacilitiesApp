package com.techticz.app.db.dao

import androidx.room.*

import com.gssirohi.techticz.database.dao.BaseDao
import com.techticz.database.model.DB_VoiceBook

@Dao
abstract class VoiceBookDao: BaseDao<DB_VoiceBook>()