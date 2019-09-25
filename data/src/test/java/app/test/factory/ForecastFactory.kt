package com.techticz.app.test.factory
import com.techticz.data.model.ForecastDataEntity
import com.techticz.domain.model.ForecastData
import com.techticz.remote.test.app.test.factory.DataFactory


class ForecastFactory {

    companion object {

        fun makeForecastEntity(): ForecastDataEntity {
            return ForecastDataEntity(DataFactory.randomUuid(), DataFactory.randomFloat(),
                DataFactory.makeFloatList(5))
        }
    }

}