import com.techticz.data.mapper.DataToDomainEntityMapper_Forecast
import com.techticz.app.test.factory.ForecastFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class DataFromDataMapperTest {

    private lateinit var mapper : DataToDomainEntityMapper_Forecast

    @Before
    fun setUp(){
        mapper = DataToDomainEntityMapper_Forecast()
    }

    @Test
    fun mapFromRemoteData(){
        var entity = ForecastFactory.makeForecastEntity()
        var data = mapper.mapFromDataToDomain(entity)

        assertEquals(entity.currentTemp,data.currentTemp)
        assertEquals(entity.locationName,data.locationName)
        assertEquals(entity.forecastTemp.get(0),data.forecastTemp.get(0))
    }
}