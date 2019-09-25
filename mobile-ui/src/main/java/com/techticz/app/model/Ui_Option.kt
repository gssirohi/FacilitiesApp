package com.techticz.ui.model

import com.techticz.data.model.ExclusiveItem

data class Ui_Option(val name:String, val icon:String, val id:Int,var allowed:Boolean,var selected:Boolean,var exclusiveItemList:MutableList<ExclusiveItem> = mutableListOf(),var exclusiveTo:MutableList<Int>?) {
    fun addExclusionInfo(exclusiveItems: List<ExclusiveItem>) {
        exclusiveItems.forEach {
            if(it.options_id != id){
                exclusiveItemList.add(it)
            }
        }
    }
}