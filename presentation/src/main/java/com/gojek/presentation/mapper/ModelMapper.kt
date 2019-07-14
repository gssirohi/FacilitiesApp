package com.gojek.presentation.mapper

interface ModelMapper<in M,out E> {
    fun mapFromData(type:M):E
}