package com.gojek.mygojekapp.test.factory
import com.gojek.data.model.ForecastDataEntity
import com.gojek.domain.model.ForecastData
import com.gojek.remote.test.mygojekapp.test.factory.DataFactory


class ForecastFactory {

    companion object {

        fun makeForecastEntity(): ForecastDataEntity {
            return ForecastDataEntity(DataFactory.randomUuid(), DataFactory.randomFloat(),
                DataFactory.makeFloatList(5))
        }
    }

}