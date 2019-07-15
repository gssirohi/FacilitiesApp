import com.gojek.data.mapper.ForeCastDataMapper
import com.gojek.mygojekapp.test.factory.ForecastFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class DataFromDataMapperTest {

    private lateinit var mapper : ForeCastDataMapper

    @Before
    fun setUp(){
        mapper = ForeCastDataMapper()
    }

    @Test
    fun mapFromRemoteData(){
        var entity = ForecastFactory.makeForecastEntity()
        var data = mapper.mapFromEntity(entity)

        assertEquals(entity.currentTemp,data.currentTemp)
        assertEquals(entity.locationName,data.locationName)
        assertEquals(entity.forecastTemp.get(0),data.forecastTemp.get(0))
    }
}