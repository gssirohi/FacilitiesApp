package com.gojek.mygojekapp.forecast

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gojek.mygojekapp.R
import kotlinx.android.synthetic.main.forecast_item_layout.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class ForecastAdapter @Inject constructor(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var forecastItems:List<Float> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = View.inflate(parent.context, R.layout.forecast_item_layout,null)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        view.layoutParams = params
        return ForecastViewHolder(view)
    }

    override fun getItemCount(): Int {
        return forecastItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = forecastItems.get(position)
        //var date = AppUtils.convertFromYYYYMMDDToDate(item.date)

        var cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR,position)
        (holder as ForecastViewHolder).forecastView.tv_day.text = ""+cal.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG,Locale.getDefault())
        (holder as ForecastViewHolder).forecastView.tv_day_temp.text = ""+item?.roundToInt()+" C"
    }

    class ForecastViewHolder(val forecastView: View) : RecyclerView.ViewHolder(forecastView) {}
}