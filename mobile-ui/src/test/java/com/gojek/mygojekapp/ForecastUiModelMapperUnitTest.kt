package com.gojek.mygojekapp

import com.gojek.mygojekapp.mapper.ForecastUiModelMapper
import com.gojek.mygojekapp.test.factory.ForecastFactory
import org.junit.Test


import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ForecastUiModelMapperUnitTest {
   // private lateinit var mapper : ForecastUiModelMapper

    @Before
    fun setUp(){
  //      mapper = ForecastUiModelMapper()
    }

    @Test
    fun mapToUiModel(){
      /*  val presentationModel = ForecastFactory.makeForecastModel()
        val uiModel = mapper.mapToUiModel(presentationModel)

        assertEquals(presentationModel.currentTemp,uiModel.currentTemp)
        assertEquals(presentationModel.locationName,uiModel.locationName)
        assertEquals(presentationModel.forecastTemp.size,uiModel.forecastTemp.size)
        assertEquals(presentationModel.forecastTemp.get(0),uiModel.forecastTemp.get(0))*/
    }
}
