package com.techticz.app.ui.facility

import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.gssirohi.techticz.voicebook.ui.facility.FacilityAdapter
import com.techticz.app.R
import com.techticz.data.model.FacilityData
import com.techticz.domain.model.FacilityDataRequest
import com.techticz.presentation.ViewModelFactory
import com.techticz.presentation.viewmodel.facility.FacilityViewModel
import com.techticz.ui.mapper.DataToUiEntityMapper_FacilityData
import com.techticz.ui.model.Ui_Facility
import com.techticz.ui.model.Ui_FacilityData
import com.techticz.ui.model.Ui_Option
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_facility.*
import kotlinx.android.synthetic.main.content_main.*
import org.buffer.android.boilerplate.presentation.data.ResourceState
import javax.inject.Inject

class FacilityActivity : AppCompatActivity(),FacilityAdapter.FacilityListner,
    FacilityOptionFragment.OnFacilityOptionsInteractionListener {

    var uiFacilityData: Ui_FacilityData = Ui_FacilityData()
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var uiMapper:DataToUiEntityMapper_FacilityData

    private lateinit var facilityViewModel: FacilityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facility)
        AndroidInjection.inject(this)

        /*val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)*/
        list_facilities.adapter = FacilityAdapter(this)
        list_facilities.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1.02f)
                .setMinScale(0.7f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.CENTER) // CENTER is a default one
                .build());
        vp_facility_options.adapter = FacilityOptionsViewPagerAdapter(this,supportFragmentManager)
        vp_facility_options.addOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener(){
            override fun onPageSelected(position: Int) {

            }
        })
        list_facilities.addOnItemChangedListener { viewHolder, i ->
            if(i > 0){
                if(uiFacilityData.facilities[i-1].selectedOption == null){
                    list_facilities.smoothScrollToPosition(i-1)
                } else {
                    vp_facility_options.currentItem = i
                }
            } else {
                vp_facility_options.currentItem = i
            }
        }
        fab_done.setOnClickListener {
            onProceed()
        }
        facilityViewModel = ViewModelProviders.of(this,viewModelFactory).get(FacilityViewModel::class.java)

        getFacilityViewModel().getFacilitiesLiveData().observe(this, androidx.lifecycle.Observer {
            if (it != null) this.onFacilityDataChanged(it.status, it.data, it.message)
        })
        facilityViewModel.fetchFacilities(FacilityDataRequest(false))
    }

    private fun onProceed() {
        SelectionBottomSheetFragment.newInstance().show(supportFragmentManager, "dialog")
    }

    private fun onFacilityDataChanged(status: ResourceState, data: FacilityData?, message: String?) {
        when(status){
            ResourceState.LOADING->{
                setupScreenForLoadingState()
            }
            ResourceState.SUCCESS->{
                setupScreenForSuccess()
                uiFacilityData = uiMapper.mapFromData(data!!)
                uiFacilityData.enableFacility(0)
                list_facilities.adapter!!.notifyDataSetChanged()
                vp_facility_options.adapter!!.notifyDataSetChanged()
            }
            ResourceState.ERROR->{
                setupScreenForError(message)
            }
        }
    }

    fun getFacilityViewModel(): FacilityViewModel {
        return facilityViewModel
    }

    override fun onStart() {
        super.onStart()
    }



    fun setupScreenForSuccess() {
        loading_view.visibility = View.GONE
        error_view.visibility = View.GONE
        container_facilities.visibility = View.VISIBLE
    }

    fun setupScreenForError(message: String?) {
        loading_view.visibility = View.GONE
     //   container.visibility = View.GONE
        error_view.visibility = View.VISIBLE
        error_view.setError(message)
    }

    fun setupScreenForLoadingState() {
        loading_view.visibility = View.VISIBLE
      //  container.visibility = View.GONE
        error_view.visibility = View.GONE
    }

    override fun onFacilityOptionSelected(facility_id:Int,option: Ui_Option) {
        var nextFacilityIndex = uiFacilityData.handleOptionSelection(facility_id,option)

        list_facilities.adapter!!.notifyDataSetChanged()
        (vp_facility_options.adapter as FacilityOptionsViewPagerAdapter).refresh()

        if(nextFacilityIndex != -1){
            list_facilities.smoothScrollToPosition(nextFacilityIndex)
        }
        var selectionCompleted = uiFacilityData.facilities.get(uiFacilityData.facilities.size-1).selectedOption != null
        when(selectionCompleted){
            true->{
                (fab_done as View).visibility = View.VISIBLE
            }
            else->(fab_done as View).visibility = View.INVISIBLE
        }
    }

    override fun getFacilityOptions(facility_id:Int): List<Ui_Option> {
        return uiFacilityData.getFacilityOptions(facility_id)
    }

    override fun onFacilityClicked(facility: Ui_Facility) {

    }

    override fun getFacilities(): List<Ui_Facility> {
        return uiFacilityData.facilities
    }


}
