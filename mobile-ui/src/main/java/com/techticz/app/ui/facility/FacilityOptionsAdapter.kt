package com.techticz.app.ui.facility

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.techticz.app.R


import com.techticz.app.ui.facility.FacilityOptionFragment.OnFacilityOptionsInteractionListener
import com.techticz.app.utils.AppUtils
import com.techticz.ui.model.Ui_Option

import kotlinx.android.synthetic.main.facility_option_card_layout.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnFacilityOptionsInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class FacilityOptionsAdapter(
    private val mListener: OnFacilityOptionsInteractionListener?, val facility_id:Int
) : RecyclerView.Adapter<FacilityOptionsAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Ui_Option
            mListener?.onFacilityOptionSelected(facility_id,item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.facility_option_card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = mListener!!.getFacilityOptions(facility_id).get(position)
        holder.bindData(option)
    }

    override fun getItemCount():Int{
        return mListener!!.getFacilityOptions(facility_id).size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        fun bindData(option:Ui_Option){
            mView.tv_option_title.text = option.name
            mView.iv_option_icon.setImageResource(AppUtils.getIconRes(option.icon))
            if(option.exclusiveTo!!.isEmpty()){
                if(option.selected){
                    mView.card_option.setCardBackgroundColor(mView.context.resources.getColor(R.color.color_1))
                    mView.iv_option_selected.visibility = View.VISIBLE
                } else {
                    mView.card_option.setCardBackgroundColor(mView.context.resources.getColor(R.color.color_9))
                    mView.iv_option_selected.visibility = View.INVISIBLE
                }
                with(mView) {
                    tag = option
                    setOnClickListener(mOnClickListener)
                }
            } else {
                mView.card_option.setCardBackgroundColor(mView.context.resources.getColor(R.color.grey))
                mView.iv_option_selected.visibility = View.INVISIBLE
                with(mView) {
                    tag = option
                    setOnClickListener(null)
                }
            }

        }
    }
}
