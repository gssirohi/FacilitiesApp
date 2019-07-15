package com.gojek.mygojekapp.forecast

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.gojek.data.model.ForecastDataEntity
import com.gojek.domain.model.ForecastData
import com.gojek.mygojekapp.R
import com.gojek.mygojekapp.test.TestApplication
import com.gojek.mygojekapp.test.util.ForecastFactory
import com.gojek.mygojekapp.test.util.RecyclerViewMatcher
import com.gojek.mygojekapp.utils.AppUtils
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.content_curr_weather.view.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.lang.RuntimeException
import kotlin.math.roundToInt

@RunWith(AndroidJUnit4::class)
class ForecastActivityTest{

    @Rule @JvmField
    val activity = ActivityTestRule<ForecastActivity>(ForecastActivity::class.java,false,false)

    @Test
    fun activityLaunches(){
        stubForecastDataManagerGetForecast(Flowable.just(ForecastFactory.makeForecastData()))
        activity.launchActivity(null)
    }

    @Test
    fun currentLocationDisplay(){
        var data = ForecastFactory.makeForecastData()
        stubForecastDataManagerGetForecast(Flowable.just(data))
        activity.launchActivity(null)

        checkLocationDisplay(data)
    }

    private fun checkLocationDisplay(data: ForecastData) {
        var expectedText = data.locationName
        onView(withText(expectedText.toString())).check(matches(isDisplayed()))
    }

    @Test
    fun currentWeatherDisplay(){
        var data = ForecastFactory.makeForecastData()
        stubForecastDataManagerGetForecast(Flowable.just(data))
        activity.launchActivity(null)

        checkCurrWeatherDetailsDisplay(data)
    }

    @Test
    fun forcastWeatherDisplay(){
        var data = ForecastFactory.makeForecastData()
        stubForecastDataManagerGetForecast(Flowable.just(data))
        activity.launchActivity(null)

        checkForecastItem(data,0)
    }

    @Test
    fun errorDisplay(){
        stubForecastRemoteRepositoryForError()
        activity.launchActivity(null)

        checkErrorDisplay()
    }

    private fun checkErrorDisplay() {
      //  onView(withText("RETRY")).check(matches(isDisplayed()))
    }

    private fun checkCurrWeatherDetailsDisplay(data: ForecastData) {
        var expectedText = AppUtils.formateInDegreeCelcius(data.currentTemp)
        onView(withText(expectedText.toString())).check(matches(isDisplayed()))

    }

    private fun checkForecastItem(data:ForecastData,position:Int){
        var expectedTemp = AppUtils.getFormattedForcastTemp(data.forecastTemp.get(position))
        var expectedDayName = AppUtils.getDayNameForPosition(position)
        onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_forecast).atPosition(position))
            .check(matches(hasDescendant(withText(expectedDayName))))
        onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_forecast).atPosition(position))
            .check(matches(hasDescendant(withText(expectedTemp))))
    }

    private fun stubForecastDataManagerGetForecast(single: Flowable<ForecastData>){
        whenever(TestApplication.appComponent().weatherDataManager().getForecast(Mockito.anyString(),
            Mockito.anyInt()))
            .thenReturn(single)
    }

    private fun stubForecastRemoteRepositoryForError(){
        whenever(TestApplication.appComponent().remoreRepository().getForeCast(Mockito.anyString(),
            Mockito.anyInt()))
            .thenReturn(Flowable.error(RuntimeException()))
    }

}