package mygojekapp

import com.gojek.mygojekapp.test.factory.ForecastFactory
import com.gojek.remote.RemoteRepositoryImpl
import com.gojek.remote.WeatherService
import com.gojek.remote.mapper.ForecastDataEntityMapper
import com.gojek.remote.model.ForecastResponse
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.util.*

@RunWith(JUnit4::class)
class RemoteRepositoryImplTest {

    private lateinit var entityMapper: ForecastDataEntityMapper
    private lateinit var weatherService: WeatherService

    private lateinit var remoteRepo: RemoteRepositoryImpl

    @Before
    fun setUp(){
        entityMapper = ForecastDataEntityMapper()
        weatherService = mock()
        remoteRepo = RemoteRepositoryImpl(weatherService,entityMapper)
    }

    @Test
    fun getForecastCompletes(){
        stubWeatherServiceGetForecast(Flowable.just(ForecastFactory.makeForecastResponse()))
        var testObservable = remoteRepo.getForeCast("Bangalore",5).test()
        testObservable.assertComplete()
    }
    @Test
    fun getForecastReturnCorrectResponse(){
        val resp = ForecastFactory.makeForecastResponse()
        val flowableResp = Flowable.just(resp)
        stubWeatherServiceGetForecast(flowableResp)
        val entity = entityMapper.mapFromRemote(resp)
        val testObservable = remoteRepo.getForeCast("Bangalore",5).test()
        testObservable.assertValue(entity)
    }

    private fun stubWeatherServiceGetForecast(observable: Flowable<ForecastResponse>){
        whenever(weatherService.getForecast(Mockito.anyString(),Mockito.anyString(),Mockito.anyInt())).thenReturn(observable)
    }
}