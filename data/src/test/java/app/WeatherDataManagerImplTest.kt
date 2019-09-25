package app

import com.techticz.data.datamanager.DataManagerImpl
import com.techticz.data.mapper.DataToDomainEntityMapper_Forecast
import com.techticz.data.model.ForecastDataEntity
import com.techticz.data.source.RepositoryFactory
import com.techticz.data.source.WeatherDataStoreRemoteImpl
import com.techticz.app.test.factory.ForecastFactory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class WeatherDataManagerImplTest {

    private lateinit var dataManager: DataManagerImpl

    private lateinit var dataStoreFactory: RepositoryFactory
    private lateinit var remoteDataStore: WeatherDataStoreRemoteImpl

    private lateinit var domainMapper: DataToDomainEntityMapper_Forecast

    @Before
    fun setUp(){
        dataStoreFactory = mock()
        domainMapper = DataToDomainEntityMapper_Forecast()
        remoteDataStore = mock()
        dataManager = DataManagerImpl(dataStoreFactory, domainMapper)
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
        val domain = domainMapper.mapFromDataToDomain(entity)
        val testObservable = dataManager.getForecast("Bangalore",5).test()
        testObservable.assertValue(domain)
    }

    private fun stubDatastore(observable: Flowable<ForecastDataEntity>){
        whenever(remoteDataStore.getForecast(Mockito.anyString(),Mockito.anyInt())).thenReturn(observable)
    }

    private fun stubWeatherDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(dataStoreFactory.retrieveWeatherDataStore())
            .thenReturn(remoteDataStore)
    }
}