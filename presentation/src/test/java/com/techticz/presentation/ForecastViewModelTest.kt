package com.techticz.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.techticz.domain.interactor.forecast.GetForecast
import com.techticz.domain.model.ForecastData
import com.techticz.domain.model.ForecastRequest
import com.techticz.app.test.factory.ForecastFactory
import com.techticz.presentation.forecast.ForecastViewModel
import com.techticz.presentation.mapper.ForecastModelMapper
import com.techticz.presentation.model.ForecastModel
import com.nhaarman.mockito_kotlin.*
import io.reactivex.subscribers.DisposableSubscriber
import org.buffer.android.boilerplate.presentation.data.ResourceState

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mock

@RunWith(JUnit4::class)
class ForecastViewModelTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var usecase: GetForecast
    @Mock lateinit var mapper: ForecastModelMapper

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSubscriber<ForecastData>>

    private lateinit var viewModel: ForecastViewModel

    @Before
    fun setUp() {
        captor = argumentCaptor<DisposableSubscriber<ForecastData>>()
        usecase = mock()
        mapper = mock()
        viewModel = ForecastViewModel(usecase,mapper)
    }

    @Test
    fun getForecastExecutesUseCase() {
        viewModel.fetchForecast(ForecastRequest("test",5))
        verify(usecase, times(1)).execute(any(), anyOrNull())
    }

    //<editor-fold desc="Success">
    @Test
    fun getForecasrReturnsSuccess() {
        val domainModel = ForecastFactory.makeForecastDomainData()
        val presentationModel = ForecastFactory.makeForecastModel()
        val domainModel2 = ForecastFactory.makeForecastDomainData()
        val presentationModel2 = ForecastFactory.makeForecastModel()
        stubBufferooMapperMapToView(presentationModel, domainModel)
        stubBufferooMapperMapToView(presentationModel2, domainModel2)

        viewModel.getForecast()
        viewModel.fetchForecast(ForecastRequest("Loc",5))

        verify(usecase).execute(captor.capture(), eq(ForecastRequest("Loc",5)))
        captor.firstValue.onNext(domainModel)

        assert(viewModel.getForecast().value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun getForecastReturnsDataOnSuccess() {
        val domainModel = ForecastFactory.makeForecastDomainData()
        val presentationModel = ForecastFactory.makeForecastModel()
        val domainModel2 = ForecastFactory.makeForecastDomainData()
        val presentationModel2 = ForecastFactory.makeForecastModel()
        stubBufferooMapperMapToView(presentationModel, domainModel)
        stubBufferooMapperMapToView(presentationModel2, domainModel2)

        viewModel.getForecast()
        viewModel.fetchForecast(ForecastRequest("Loc",5))

        verify(usecase).execute(captor.capture(), eq(ForecastRequest("Loc",5)))
        captor.firstValue.onNext(domainModel)

        assert(viewModel.getForecast().value?.data == presentationModel)
    }

    @Test
    fun getForecastReturnsNoMessageOnSuccess() {
        val domainModel = ForecastFactory.makeForecastDomainData()
        val presentationModel = ForecastFactory.makeForecastModel()
        val domainModel2 = ForecastFactory.makeForecastDomainData()
        val presentationModel2 = ForecastFactory.makeForecastModel()
        stubBufferooMapperMapToView(presentationModel, domainModel)
        stubBufferooMapperMapToView(presentationModel2, domainModel2)

        viewModel.getForecast()
        viewModel.fetchForecast(ForecastRequest("Loc",5))

        verify(usecase).execute(captor.capture(), eq(ForecastRequest("Loc",5)))
        captor.firstValue.onNext(domainModel)

        assert(viewModel.getForecast().value?.message == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getForecastReturnsError() {
        viewModel.getForecast()
        viewModel.fetchForecast(ForecastRequest("Loc",5))

        verify(usecase).execute(captor.capture(), eq(ForecastRequest("Loc",5)))
        captor.firstValue.onError(java.lang.RuntimeException())

        assert(viewModel.getForecast().value?.status == ResourceState.ERROR)
    }

    @Test
    fun getForecastReturnsLoading() {
        viewModel.getForecast()
        viewModel.fetchForecast(ForecastRequest("Loc",5))

        assert(viewModel.getForecast().value?.status == ResourceState.LOADING)
    }



    private fun stubBufferooMapperMapToView(preentationModel: ForecastModel,
                                            domainModel: ForecastData) {
        whenever(mapper.mapFromData(domainModel))
                .thenReturn(preentationModel)
    }

}