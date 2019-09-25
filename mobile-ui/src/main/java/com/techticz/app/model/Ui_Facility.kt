package com.techticz.ui.model

data class Ui_Facility(val facility_id:Int, val name:String, var selectedOption:Ui_Option?, var allowed:Boolean, val options:List<Ui_Option>)