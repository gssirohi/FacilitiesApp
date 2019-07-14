package com.gojek.remote.mapper

interface EntityMapper<in M,out E> {
    fun mapFromRemote(type:M):E
}