package com.techticz.app.test.factory

import com.techticz.domain.model.ForecastData
import com.techticz.remote.test.app.test.factory.DataFactory


class ForecastFactory {

    companion object {

        fun makeForecastDomainData(): ForecastData {
            return ForecastData(DataFactory.randomUuid(), DataFactory.randomFloat(),
                DataFactory.makeFloatList(5))
        }
    }

}