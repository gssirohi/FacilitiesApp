package com.gojek.data.repository

import com.gojek.data.model.ForecastDataEntity
import io.reactivex.Flowable

interface RemoteRepository {
    fun getForeCast(location:String,days:Int):Flowable<ForecastDataEntity>
}