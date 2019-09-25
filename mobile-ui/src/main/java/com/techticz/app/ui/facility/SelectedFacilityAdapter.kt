package com.gssirohi.techticz.voicebook.ui.facility

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techticz.app.R
import com.techticz.app.utils.AppUtils
import com.techticz.ui.model.Ui_Facility
import kotlinx.android.synthetic.main.facility_card_layout.view.*
import kotlinx.android.synthetic.main.facility_option_card_layout.view.*

class SelectedFacilityAdapter(val listener: FacilityAdapter.FacilityListner): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = View.inflate(parent.context, R.layout.selected_facility_layout, null)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
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

    class FacilityViewHolder(val view: View, val listner: FacilityAdapter.FacilityListner):RecyclerView.ViewHolder(view){

        init {

        }

        fun bindData(facility:Ui_Facility){
            view.tv_facility_title.text = facility.name
            view.tv_option_title.text = facility.selectedOption!!.name
            view.iv_option_icon.setImageResource(AppUtils.getIconRes(facility.selectedOption!!.icon))
        }
    }

}