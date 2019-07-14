package com.gojek.data.model

data class ForecastDataEntity(val locationName:String, val currentTemp:Float, val forecastTemp:List<Float>)