package com.gojek.mygojekapp.forecast

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ClipDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.v13.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.gojek.domain.model.ForecastRequest
import com.gojek.mygojekapp.R
import com.gojek.mygojekapp.mapper.ForecastUiModelMapper
import com.gojek.mygojekapp.utils.AppUtils
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

class ForecastActivity : AppCompatActivity(),LocationListener {

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

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                Companion.REQUEST_PERMISSION_FINE_LOCATION
            )
        } else {
            checkGPSAndFetchLocation()
        }
       // forecastViewModel.fetchForecast(ForecastRequest("Bangalore",5))
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
            tv_current_weather.text = AppUtils.formateInDegreeCelcius(uiData.currentTemp)
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

    //-------------------Location------------------------------------
    @SuppressLint("MissingPermission")
    private fun checkGPSAndFetchLocation() {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0f,this)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,0f,this)

            var lastLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            fetchWeatherForecast(lastLoc)
        } else {
            showAlertNoGPS()
        }

    }

    private fun fetchWeatherForecast(location: Location?) {
        var loc = ""+location?.latitude+","+location?.longitude
        forecastViewModel.fetchForecast(ForecastRequest(loc,5))
    }

    private fun showAlertNoGPS() {
        var dialog = AlertDialog.Builder(this)
            .setMessage(getString(R.string.alert_gps_disabled))
            .setPositiveButton("Yes",{ dialog: DialogInterface, i: Int ->
                startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),REQUEST_CODE_ENABLE_GPS)})
            .setNegativeButton("No") { dialog: DialogInterface, i: Int ->  setupScreenForError(getString(R.string.error_gps_disabled))}
            .create()
        dialog.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    checkGPSAndFetchLocation()
                } else {
                    finish()
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            Companion.REQUEST_CODE_ENABLE_GPS->checkGPSAndFetchLocation()
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        checkGPSAndFetchLocation()
    }
    override fun onProviderEnabled(provider: String?) {}
    override fun onProviderDisabled(provider: String?) {}
    override fun onLocationChanged(location: Location?) {
        fetchWeatherForecast(location)
    }
    companion object {
        const val REQUEST_PERMISSION_FINE_LOCATION: Int = 1
        const val REQUEST_CODE_ENABLE_GPS = 11
    }
}
