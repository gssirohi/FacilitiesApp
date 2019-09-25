package com.techticz.app.ui.facility

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gssirohi.techticz.voicebook.ui.facility.FacilityAdapter
import com.gssirohi.techticz.voicebook.ui.facility.SelectedFacilityAdapter
import com.techticz.app.R
import kotlinx.android.synthetic.main.fragment_bottom_sheet_selection.*

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    SelectionBottomSheetFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 *
 * You activity (or fragment) needs to implement [SelectionBottomSheetFragment.Listener].
 */
class SelectionBottomSheetFragment : BottomSheetDialogFragment() {
    private var mListener: FacilityAdapter.FacilityListner? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = SelectedFacilityAdapter(mListener!!)
        bt_done.setOnClickListener { dismiss() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment
        if (parent != null) {
            mListener = parent as FacilityAdapter.FacilityListner
        } else {
            mListener = context as FacilityAdapter.FacilityListner
        }
    }

    override fun onDetach() {
        mListener = null
        super.onDetach()
    }

    companion object {

        // TODO: Customize parameters
        fun newInstance(): SelectionBottomSheetFragment =
            SelectionBottomSheetFragment()

    }
}
