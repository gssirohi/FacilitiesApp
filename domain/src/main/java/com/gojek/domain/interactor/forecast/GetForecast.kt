package com.gojek.domain.interactor.forecast

import com.gojek.domain.datamanager.WeatherDataManager
import com.gojek.domain.interactor.FlowableUseCase
import com.gojek.domain.model.ForecastData
import com.gojek.domain.model.ForecastRequest
import io.reactivex.Flowable
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread
import org.buffer.android.boilerplate.domain.executor.ThreadExecutor
import javax.inject.Inject

open class GetForecast @Inject constructor(private val dataManager: WeatherDataManager,
                                      threadExecutor: ThreadExecutor,
                                      postThreadExecutor: PostExecutionThread) :
FlowableUseCase <ForecastData,ForecastRequest>(threadExecutor,postThreadExecutor){

    override fun buildUseCaseObservable(params: ForecastRequest?): Flowable<ForecastData> {
        return dataManager.getForecast(params!!.location,params!!.days)
    }

}