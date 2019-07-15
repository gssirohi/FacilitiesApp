package mygojekapp

import com.gojek.data.WeatherDataManagerImpl
import com.gojek.data.mapper.ForeCastDataMapper
import com.gojek.data.model.ForecastDataEntity
import com.gojek.data.source.WeatherDataStoreFactory
import com.gojek.data.source.WeatherDataStoreRemoteImpl
import com.gojek.mygojekapp.test.factory.ForecastFactory
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
class WeatherDataManagerImplTest {

    private lateinit var dataManager: WeatherDataManagerImpl

    private lateinit var dataStoreFactory: WeatherDataStoreFactory
    private lateinit var remoteDataStore: WeatherDataStoreRemoteImpl

    private lateinit var domainMapper: ForeCastDataMapper

    @Before
    fun setUp(){
        dataStoreFactory = mock()
        domainMapper = ForeCastDataMapper()
        remoteDataStore = mock()
        dataManager = WeatherDataManagerImpl(dataStoreFactory,domainMapper)
        stubWeatherDataStoreFactoryRetrieveRemoteDataStore()
    }

    @Test
    fun getForecastCompletes(){
        stubWeatherDataStoreFactoryRetrieveRemoteDataStore()
        stubDatastore(Flowable.just(ForecastFactory.makeForecastEntity()))
        var testObservable = dataManager.getForecast("Bangalore",5).test()
        testObservable.assertComplete()
    }
    @Test
    fun getForecastReturnCorrectResponse(){
        stubWeatherDataStoreFactoryRetrieveRemoteDataStore()
        val entity = ForecastFactory.makeForecastEntity()
        val flowableEntity = Flowable.just(entity)
        stubDatastore(flowableEntity)
        val domain = domainMapper.mapFromEntity(entity)
        val testObservable = dataManager.getForecast("Bangalore",5).test()
        testObservable.assertValue(domain)
    }

    private fun stubDatastore(observable: Flowable<ForecastDataEntity>){
        whenever(remoteDataStore.getForecast(Mockito.anyString(),Mockito.anyInt())).thenReturn(observable)
    }

    private fun stubWeatherDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(dataStoreFactory.retrieveDataStore())
            .thenReturn(remoteDataStore)
    }
}