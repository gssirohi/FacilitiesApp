package mygojekapp

import com.gojek.mygojekapp.test.factory.ForecastFactory
import com.gojek.remote.mapper.EntityMapper
import com.gojek.remote.mapper.ForecastDataEntityMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class DataFromResponseMapperTest {

    private lateinit var mapper : ForecastDataEntityMapper

    @Before
    fun setUp(){
        mapper = ForecastDataEntityMapper()
    }

    @Test
    fun mapFromRemoteData(){
        var response = ForecastFactory.makeForecastResponse()
        var entity = mapper.mapFromRemote(response)

        assertEquals(entity.currentTemp,response.current.temp_c)
        assertEquals(entity.locationName,response.location.name)
        assertEquals(entity.forecastTemp.get(0),response.forecast.forecastday.get(0).day.avgtemp_c)
    }
}