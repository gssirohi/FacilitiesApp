package com.gojek.data.source

import javax.inject.Inject

open class WeatherDataStoreFactory @Inject constructor(val dataStoreRemote: WeatherDataStoreRemoteImpl){
    fun retrieveDataStore(): WeatherDataStoreRemoteImpl {
        return dataStoreRemote
    }
}