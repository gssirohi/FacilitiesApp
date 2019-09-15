package com.techticz.app.ui.voicebook

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.techticz.app.R
import com.techticz.app.utils.AppPref
import com.techticz.app.utils.AppUtils
import com.techticz.database.model.VoiceBook
import kotlinx.android.synthetic.main.fragment_create_voice_book_bottom_sheet.*

import java.lang.Exception


/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    VoiceBookBottomSheetFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 *
 * You activity (or fragment) needs to implement [VoiceBookBottomSheetFragment.Listener].
 */
class VoiceBookBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var voiceBook: VoiceBook
    private lateinit var callBack: BottomSheetCallBack
    private var viewMode: Int =
        VIEW_MODE_CREATE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_voice_book_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewMode = arguments!!.getInt("viewMode",
            VIEW_MODE_CREATE
        )

        var colorCount = grid_color.childCount
        for(i in 1..colorCount){
            var colorView = grid_color.getChildAt(i-1) as ImageView
            colorView.tag = false
            colorView.setOnClickListener{
                var isSelected = it.getTag() as Boolean
                if(isSelected){
                    colorView.setBackground(null)
                    colorView.tag = false
                } else {
                    for(j in 1..colorCount){
                        (grid_color.getChildAt(j-1) as ImageView).background = null
                        (grid_color.getChildAt(j-1) as ImageView).tag = false
                    }
                    colorView.setBackgroundResource(R.drawable.stroke_button)
                    colorView.tag = true
                }
            }
        }


        voiceBook = callBack.onVoiceBookRequested()
        if(voiceBook == null){
            voiceBook = VoiceBook(-1L,"",-1,0)
        }
        et_book_title.setText(voiceBook.title)
        b_book_done.setOnClickListener{
            var msg = validateInput()
            if(!TextUtils.isEmpty(msg)){
                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            voiceBook.title = et_book_title.text.toString()
            voiceBook.color = getPickedColor()
            voiceBook.id = AppUtils.getTimeStamp()
            if(checkbox_default_voicebook.isChecked){
                AppPref.setDefaultBookId(context!!,voiceBook.id)
            }
            callBack.onVoiceBookSubmit(voiceBook,viewMode)
            dismiss()
        }

        b_book_done.text = when(viewMode){
            VIEW_MODE_CREATE -> "CREATE VOICE BOOK"
            else -> "UPDATE VOICE BOOK"
        }
    }

    private fun validateInput():String {
        if(TextUtils.isEmpty(et_book_title.text.toString())){
            return "Please enter Voice Book Title"
        }
        if(getPickedColor() == 0){
            return "Please choose color"
        }
        return ""
    }

    private fun getPickedColor(): Int {
        var colorCount = grid_color.childCount
        for(i in 1..colorCount){
            var colorView = grid_color.getChildAt(i-1) as ImageView
            var isSelected = colorView.getTag() as Boolean
            if(isSelected){
                return when(i){
                    1-> context!!.resources!!.getColor(R.color.color_1)
                    2-> context!!.resources!!.getColor(R.color.color_2)
                    3-> context!!.resources!!.getColor(R.color.color_3)
                    4-> context!!.resources!!.getColor(R.color.color_4)
                    5-> context!!.resources!!.getColor(R.color.color_5)
                    6-> context!!.resources!!.getColor(R.color.color_6)
                    7-> context!!.resources!!.getColor(R.color.color_7)
                    8-> context!!.resources!!.getColor(R.color.color_8)
                    else-> 0
                }
            }
        }
        return 0
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is BottomSheetCallBack){
            callBack = context as BottomSheetCallBack
        } else {
            throw Exception("Context is not BottomSheetCallBack!")
        }

    }

    override fun onDetach() {
        super.onDetach()
    }

    interface BottomSheetCallBack{
        fun onVoiceBookRequested(): VoiceBook
        fun onVoiceBookSubmit(book:VoiceBook, viewMode:Int)
    }

    companion object {
        var VIEW_MODE_CREATE = 11
        var VIEW_MODE_UPDATE = 12
        // TODO: Customize parameters
        fun newInstance(viewMode:Int): VoiceBookBottomSheetFragment =
            VoiceBookBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putInt("viewMode",viewMode)
                }
            }

    }
}
