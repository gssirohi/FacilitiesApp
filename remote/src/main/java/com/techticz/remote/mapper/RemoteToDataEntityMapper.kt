package com.techticz.remote.mapper

interface RemoteToDataEntityMapper<in M,out E> {
    fun mapFromRemote(type:M):E
}