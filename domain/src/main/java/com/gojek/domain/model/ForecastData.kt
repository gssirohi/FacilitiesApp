package com.gojek.domain.model

data class ForecastData(val locationName:String, val currentTemp:Float, val forecastTemp:List<Float>)