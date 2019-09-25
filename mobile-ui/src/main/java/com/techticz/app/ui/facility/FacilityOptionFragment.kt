package com.techticz.app.ui.facility

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techticz.app.R

import com.techticz.ui.model.Ui_Option
import kotlinx.android.synthetic.main.fragment_facility_option.view.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [FacilityOptionFragment.OnFacilityOptionsInteractionListener] interface.
 */
class FacilityOptionFragment : Fragment() {

    var facilityId:Int = -1

    private var listener: OnFacilityOptionsInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            facilityId = it.getInt(FACILITY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_facility_option, container, false)

        // Set the adapter
        view.list_facility_options.adapter = FacilityOptionsAdapter(listener,facilityId)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFacilityOptionsInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFacilityOptionsInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun refresh() {
        view!!.list_facility_options.adapter!!.notifyDataSetChanged()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFacilityOptionsInteractionListener {
        fun onFacilityOptionSelected(facility_id:Int,option: Ui_Option)
        fun getFacilityOptions(position:Int): List<Ui_Option>
    }

    companion object {

        // TODO: Customize parameter argument names
        const val FACILITY_ID = "FACILITY_ID"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(facilityId: Int) =
            FacilityOptionFragment().apply {
                arguments = Bundle().apply {
                    putInt(FACILITY_ID, facilityId)
                }
            }
    }
}
