package com.gojek.presentation.model

data class ForecastModel(val locationName:String, val currentTemp:Float, val forecastTemp:List<Float>)