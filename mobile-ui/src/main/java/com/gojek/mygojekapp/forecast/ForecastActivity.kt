package com.gojek.mygojekapp.forecast

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gojek.domain.model.ForecastRequest
import com.gojek.mygojekapp.R
import com.gojek.mygojekapp.mapper.ForecastUiModelMapper
import com.gojek.presentation.ViewModelFactory
import com.gojek.presentation.forecast.ForecastViewModel
import com.gojek.presentation.model.ForecastModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_forecast.*
import kotlinx.android.synthetic.main.content_curr_weather.*
import org.buffer.android.boilerplate.presentation.data.ResourceState
import javax.inject.Inject
import kotlin.math.roundToInt

class ForecastActivity : AppCompatActivity() {

    @Inject lateinit var mapper:ForecastUiModelMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var forecastViewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)
        AndroidInjection.inject(this)

        forecastViewModel = ViewModelProviders.of(this,viewModelFactory).get(ForecastViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        forecastViewModel.getForecast().observe(this, Observer{
            if(it!=null) this.onDataStateChanged(it.status,it.data,it.message)
        })

        forecastViewModel.fetchForecast(ForecastRequest("Bangalore",5))
    }

    private fun onDataStateChanged(status: ResourceState, data: ForecastModel?, message: String?) {
        when (status) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(data)
            ResourceState.ERROR -> setupScreenForError(message)
        }
    }

    private fun setupScreenForSuccess(data: ForecastModel?) {
        loading_view.visibility = View.GONE
        error_view.visibility = View.GONE
        main_content.visibility = View.VISIBLE
        if(data != null) {
            var uiData = mapper.mapToUiModel(data!!)
            tv_current_weather.text = ""+uiData.currentTemp.roundToInt()
            tv_location.text = ""+uiData.locationName
        }
    }

    private fun setupScreenForError(message: String?) {
        loading_view.visibility = View.GONE
        main_content.visibility = View.GONE
        error_view.visibility = View.VISIBLE
    }

    private fun setupScreenForLoadingState() {
        loading_view.visibility = View.VISIBLE
        main_content.visibility = View.GONE
        error_view.visibility = View.GONE
    }
}
