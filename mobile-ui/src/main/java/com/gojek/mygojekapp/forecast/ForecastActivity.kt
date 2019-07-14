package com.gojek.mygojekapp.forecast

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.ClipDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.gojek.domain.model.ForecastRequest
import com.gojek.mygojekapp.R
import com.gojek.mygojekapp.mapper.ForecastUiModelMapper
import com.gojek.mygojekapp.widget.error.ErrorListener
import com.gojek.presentation.ViewModelFactory
import com.gojek.presentation.forecast.ForecastViewModel
import com.gojek.presentation.model.ForecastModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_forecast.*
import kotlinx.android.synthetic.main.bottom_sheet_forecast_weather.*
import kotlinx.android.synthetic.main.content_curr_weather.*
import org.buffer.android.boilerplate.presentation.data.ResourceState
import javax.inject.Inject
import kotlin.math.roundToInt

class ForecastActivity : AppCompatActivity() {

    @Inject lateinit var mapper:ForecastUiModelMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var adapter: ForecastAdapter

    private lateinit var forecastViewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)
        AndroidInjection.inject(this)

        forecastViewModel = ViewModelProviders.of(this,viewModelFactory).get(ForecastViewModel::class.java)
        setUpMainContent()
        setUpListners()
    }

    private fun setUpListners() {
        error_view.errorListener = object:ErrorListener{
            override fun onTryAgainClicked() {
                forecastViewModel.fetchForecast(ForecastRequest("Delhi",5))
            }
        }
    }


    private var behavior: BottomSheetBehavior<ConstraintLayout>? = null

    private fun setUpMainContent() {
        recycler_forecast.layoutManager = LinearLayoutManager(this)
        var decorationItem = DividerItemDecoration(this, ClipDrawable.HORIZONTAL)
        decorationItem.setDrawable(resources.getDrawable(R.drawable.separator))
        recycler_forecast.addItemDecoration(decorationItem)

        recycler_forecast.adapter = adapter

        behavior = BottomSheetBehavior.from(bottom_sheet_forecast)
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
            adapter.forecastItems = uiData.forecastTemp
            adapter.notifyDataSetChanged()
            showBottomSheet()
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

    private fun showBottomSheet(){
        Handler().postDelayed({behavior!!.state = BottomSheetBehavior.STATE_EXPANDED},200)
    }
}
