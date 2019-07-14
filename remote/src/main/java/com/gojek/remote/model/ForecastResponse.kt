package com.gojek.remote.model

data class ForecastResponse(val location:WeatherLocation,val current:CurrentWeather, val forecast:ForeCast)

data class WeatherLocation(val name:String)

data class CurrentWeather(val temp_c:Float)

data class ForeCast(val forecastday:List<DayForeCast>)

data class DayForeCast(val date:String, val day:Day)

data class Day(val avgtemp_c:Float)