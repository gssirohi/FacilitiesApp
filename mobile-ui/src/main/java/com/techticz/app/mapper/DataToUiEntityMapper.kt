package com.techticz.ui.mapper

interface DataToUiEntityMapper<in M,out E> {
    fun mapFromData(type:M):E
}