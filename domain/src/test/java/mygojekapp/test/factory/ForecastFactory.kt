package com.gojek.mygojekapp.test.factory

import com.gojek.domain.model.ForecastData
import com.gojek.remote.test.mygojekapp.test.factory.DataFactory


class ForecastFactory {

    companion object {

        fun makeForecastDomainData(): ForecastData {
            return ForecastData(DataFactory.randomUuid(), DataFactory.randomFloat(),
                DataFactory.makeFloatList(5))
        }
    }

}