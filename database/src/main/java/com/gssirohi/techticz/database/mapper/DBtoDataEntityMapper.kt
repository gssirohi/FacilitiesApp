package com.techticz.database.mapper

interface DBtoDataEntityMapper<M,E> {
    fun mapFromDB(type:M):E
    fun mapToDB(dataEntity:E):M
}