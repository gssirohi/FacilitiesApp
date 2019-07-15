package com.gojek.mygojekapp.test.factory
import com.gojek.domain.model.ForecastData
import com.gojek.remote.model.*
import com.gojek.remote.test.mygojekapp.test.factory.DataFactory


class ForecastFactory {

    companion object {

        fun makeForecastResponse(): ForecastResponse {
            var weatherLocation = WeatherLocation("Test Location")
            var currWeather = CurrentWeather(DataFactory.randomFloat())
            var items = DataFactory.makeFloatList(5)
            var mutableList: MutableList<DayForeCast> = mutableListOf()
            items.forEach {
                mutableList.add(DayForeCast("date", Day(it)))
            }
            return ForecastResponse(weatherLocation, currWeather, ForeCast(mutableList))
        }
    }

}