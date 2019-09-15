package com.techticz.app.ui.voicememo

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techticz.app.R
import com.techticz.data.model.VoiceMemo
import kotlinx.android.synthetic.main.fragment_voice_memo_bottom_sheet.*
import java.lang.Exception


/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    VoiceMemoBottomSheetFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 *
 * You activity (or fragment) needs to implement [VoiceMemoBottomSheetFragment.Listener].
 */
class VoiceMemoBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var voiceMemo: VoiceMemo
    private lateinit var callBack: BottomSheetCallBack
    private var viewMode: Int = VIEW_MODE_CREATE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_voice_memo_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewMode = arguments!!.getInt("viewMode", VIEW_MODE_CREATE)
        var bgColor = arguments!!.getInt("background", VIEW_MODE_CREATE)
        getView()?.setBackgroundColor(bgColor)
        voiceMemo = callBack.onVoiceMemoRequested()
        et_memo_title.setText(voiceMemo.title)
        et_memo_desc.setText(voiceMemo.desc)
        tv_audio_name.text = voiceMemo.audio.fileName

        b_memo_done.setOnClickListener{
            callBack.onVoiceMemoSubmit(voiceMemo,viewMode)
            dismiss()
        }

        b_memo_done.text = when(viewMode){
            VIEW_MODE_CREATE-> "CREATE VOICE MEMO"
            else -> "UPDATE VOICE MEMO"
        }
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
        fun onVoiceMemoRequested(): VoiceMemo
        fun onVoiceMemoSubmit(memo:VoiceMemo, viewMode:Int)
    }

    companion object {
        var VIEW_MODE_CREATE = 11
        var VIEW_MODE_UPDATE = 12
        // TODO: Customize parameters
        fun newInstance(viewMode:Int,background:Int): VoiceMemoBottomSheetFragment =
            VoiceMemoBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putInt("viewMode",viewMode)
                    putInt("background",background)
                }
            }

    }
}
