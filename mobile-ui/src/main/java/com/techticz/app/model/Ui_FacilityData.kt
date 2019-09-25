package com.techticz.ui.model

import android.view.View
import com.techticz.app.ui.facility.FacilityOptionsViewPagerAdapter
import com.techticz.data.model.Exclusion
import com.techticz.data.model.ExclusiveItem
import kotlinx.android.synthetic.main.activity_facility.*
import kotlinx.android.synthetic.main.content_main.*

data class Ui_FacilityData(var facilities:List<Ui_Facility> = mutableListOf(), var exclusions:List<Exclusion> = mutableListOf()) {

    fun retriveOption(e: ExclusiveItem): Ui_Option? {
        facilities.forEach {
            if(it.facility_id == e.facility_id){
                it.options.forEach {
                    if(it.id == e.options_id){
                        return it
                    }
                }
            }
        }
        return null
    }

    fun handleOptionSelection(facility_id: Int, option: Ui_Option):Int {
        var moveToNextIndex = -1
        var index = -1
        facilities.forEach {
            index++
            if(it.facility_id == facility_id){
                //check if already selected
                if(it.selectedOption == null){
                    it.selectedOption = option
                    it.options.forEach{
                        it.selected = it.id == option.id
                    }
                    if(enableFacility(index+1)){
                        moveToNextIndex = index+1
                    }
                    excludeOptions(option)
                } else if(it.selectedOption!!.id == option.id){

                } else {
                    //selection has changed so clear selection of next facility and so on
                    deselectFacility(index+1)
                    it.selectedOption = option
                    it.options.forEach{
                        it.selected = it.id == option.id
                        if(it.selected){
                            excludeOptions(it)
                        } else {
                            includeOptions(it)
                        }
                    }
                    excludeOptions(option)
                }

                return moveToNextIndex
            }
        }
        return moveToNextIndex
    }

    private fun excludeOptions(option: Ui_Option) {
        option.exclusiveItemList.forEach {
            var exclusiveOption = retriveOption(it)
            exclusiveOption!!.exclusiveTo!!.add(option.id)
        }
    }

    private fun includeOptions(option: Ui_Option) {
        option.exclusiveItemList.forEach {
            var exclusiveOption = retriveOption(it)
            if(exclusiveOption!!.exclusiveTo!!.contains(option.id)) {
                exclusiveOption!!.exclusiveTo!!.remove(option.id)
            }
        }
    }

    private fun deselectFacility(i: Int) {
        if(i < facilities.size) {
            facilities.get(i).selectedOption = null
            facilities.get(i).allowed = true
            facilities.get(i).options.forEach {
                it.selected = false
                includeOptions(it)
            }
            disableAllFacilityFromHere(i + 1)
        }
    }

    private fun disableAllFacilityFromHere(index: Int) {
        if(index < facilities.size) {
            var last = facilities.size - 1
            for (i in index..last) {
                facilities.get(index).allowed = false
                facilities.get(index).selectedOption = null
                facilities.get(index).options.forEach {
                    //it.allowed = false
                    it.selected = false
                    //it.exclusiveTo = null
                    includeOptions(it)
                }
            }
        }
    }

    fun enableFacility(index: Int):Boolean {
        if(index < facilities.size){

            facilities.get(index).allowed = true
            facilities.get(index).options.forEach {
                it.allowed = it.exclusiveTo!!.isEmpty()
            }

            return index > 0
        }
        return false
    }

     fun getFacilityOptions(facility_id:Int): List<Ui_Option> {
        facilities.forEach {
            if(it.facility_id == facility_id){
                return it.options
            }
        }
        return mutableListOf()
    }
}
