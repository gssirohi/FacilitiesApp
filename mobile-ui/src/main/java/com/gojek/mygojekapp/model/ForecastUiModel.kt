package com.gojek.mygojekapp.model

data class ForecastUiModel(val locationName:String, val currentTemp:Float, val forecastTemp:List<Float>)