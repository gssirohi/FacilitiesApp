package com.techticz.app.ui.facility

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FacilityOptionsViewPagerAdapter (val activity:FacilityActivity, val fm: FragmentManager?) :
    FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        var facility = activity.uiFacilityData.facilities.get(position)
        fm!!.fragments.forEach {
            if(it is FacilityOptionFragment){
                it.facilityId = facility.facility_id
                return it
            }
        }
        var frag = FacilityOptionFragment.newInstance(facility.facility_id)
        return frag
    }

    override fun getCount(): Int {
        return activity.uiFacilityData.facilities.size
    }

    open fun refresh(){
        notifyDataSetChanged()
        fm!!.fragments.forEach {
            if(it is FacilityOptionFragment){
                it.refresh()
            }
        }
    }

}