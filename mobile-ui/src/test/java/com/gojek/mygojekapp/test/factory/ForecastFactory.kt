package com.gojek.mygojekapp.test.factory
import com.gojek.domain.model.ForecastData
import com.gojek.presentation.model.ForecastModel


object ForecastFactory {

    fun makeForecastModel(): ForecastModel {
        return ForecastModel(DataFactory.randomUuid(), DataFactory.randomFloat(),
            DataFactory.makeFloatList(5))
    }

}