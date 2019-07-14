package com.gojek.mygojekapp.test.util

import com.gojek.domain.model.ForecastData
import com.gojek.presentation.model.ForecastModel


object ForecastFactory {

    fun makeForecastData(): ForecastData {
        return ForecastData(DataFactory.randomUuid(), DataFactory.randomFloat(),
                DataFactory.makeFloatList(5))
    }

    fun makeForecastModel(): ForecastModel {
        return ForecastModel(DataFactory.randomUuid(), DataFactory.randomFloat(),
            DataFactory.makeFloatList(5))
    }

}