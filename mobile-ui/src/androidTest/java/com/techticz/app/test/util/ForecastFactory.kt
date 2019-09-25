package com.techticz.app.test.util

import com.techticz.domain.model.ForecastData
import com.techticz.presentation.model.ForecastModel


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