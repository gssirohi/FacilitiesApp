package com.gssirohi.techticz.voicebook.ui.facility

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techticz.app.R
import com.techticz.ui.model.Ui_Facility
import kotlinx.android.synthetic.main.facility_card_layout.view.*

class FacilityAdapter(val listener:FacilityListner): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = View.inflate(parent.context, R.layout.facility_card_layout, null)
        return FacilityViewHolder(view,listener)
    }

    override fun getItemCount(): Int {
        return listener.getFacilities().size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when{
            holder is FacilityViewHolder->{
                holder.bindData(listener.getFacilities().get(position))
            }
        }
    }

    class FacilityViewHolder(val view: View, val listner:FacilityListner):RecyclerView.ViewHolder(view){

        init {

        }

        fun bindData(facility:Ui_Facility){
            view.tv_facility_title.text = facility.name
            view.tv_options_count.text = "${facility.options.size} options"
            if(facility.allowed){
                if(facility.selectedOption != null) {
                    view.iv_facility_option_selected.visibility = View.VISIBLE
                    view.card_facility.setCardBackgroundColor(view.context.resources.getColor(R.color.color_1))
                } else {
                    view.iv_facility_option_selected.visibility = View.GONE
                    view.card_facility.setCardBackgroundColor(view.context.resources.getColor(R.color.color_9))
                }
            } else {
                view.iv_facility_option_selected.visibility = View.GONE
                view.card_facility.setCardBackgroundColor(view.context.resources.getColor(R.color.grey))
            }

            view.setOnClickListener { listner.onFacilityClicked(facility) }
        }
    }


    interface FacilityListner{
        fun onFacilityClicked(facility: Ui_Facility)
        fun getFacilities():List<Ui_Facility>
    }
}