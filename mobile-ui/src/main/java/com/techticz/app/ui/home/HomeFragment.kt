package com.techticz.app.ui.home

import android.content.ClipData
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.gssirohi.techticz.voicebook.ui.voicebook.VoiceBookAdapter
import com.gssirohi.techticz.voicebook.ui.voicebook.VoicebookActivity
import com.gssirohi.techticz.voicebook.ui.voicememo.VoiceMemoAdapter
import com.gssirohi.techticz.voicebook.ui.voicememo.VoiceMemoCard
import com.techticz.app.R
import com.techticz.app.utils.AppPref
import com.techticz.data.model.MemoAudio
import com.techticz.data.model.VoiceMemo
import com.techticz.database.model.VoiceBook
import com.techticz.domain.model.VoiceBooksRequest
import com.techticz.domain.model.VoiceMemosRequest
import com.techticz.presentation.viewmodel.home.HomeViewModel
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.voice_memo_card_layout.view.*
import org.buffer.android.boilerplate.presentation.data.ResourceState
import java.io.File
import android.net.Uri


class HomeFragment : Fragment(), VoiceMemoAdapter.MemoListner {
    override fun onCreateDefaultMemo() {
        var id = AppPref.getDefaultBookId(context!!)
        var books = (activity as MainActivity).getVoiceBookViewModel().getVoiceBooks().value
        books?.data?.forEach{
            if(it.id == id){
                var i = Intent(context, VoicebookActivity::class.java)
                i.putExtra("voiceBookId", id)
                i.putExtra("voiceBookColor", it.color)
                i.putExtra("voiceBookName", it.title)
                i.putExtra("startRecording",true)
                context!!.startActivity(i)
            }
        }

    }

    override fun onPlayClicked(memo: VoiceMemo, position: Int) {
        for(i in 1..scroller_recent_memo.childCount){
            var view = scroller_recent_memo.getChildAt(i-1)
            if((view as VoiceMemoCard).getMemo().id != memo.id) {
                (view as VoiceMemoCard).memo_audio_player.stopAudio()
            }
        }
    }

    override fun onMemoShareClicked(memo: VoiceMemo, position: Int) {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            putExtra(Intent.EXTRA_TEXT, memo.desc+"\n\n -- sent via VoiceBook App")

            // (Optional) Here we're setting the title of the content
            putExtra(Intent.EXTRA_TITLE, "Voice Memo")
            val extraMimeTypes = arrayOf("audio/*","text/plain")
            putExtra(Intent.EXTRA_MIME_TYPES, extraMimeTypes)
            putExtra(Intent.EXTRA_STREAM, Uri.parse(memo.audio.localPath+"/"+memo.audio.fileName))
            type = "audio/*"

            setClipData(ClipData.newRawUri("Voice",Uri.parse(memo.audio.localPath+"/"+memo.audio.fileName)));
            setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }, "Share Voice Memo")
        startActivity(share)

    }

    override fun onMemoDeleteClicked(memo: VoiceMemo) {
        try {
            var file = File(memo.audio.localPath + File.separator + memo.audio.fileName)
            if (file.exists()) {
                file.delete()
            }
        } catch (e:Exception){
            e.printStackTrace()
        }
        (activity as MainActivity).getVoiceBookViewModel().deleteVoiceMemo(memo)
    }

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

        root.list_books.adapter = VoiceBookAdapter(activity as MainActivity)

        root.scroller_recent_memo.adapter = VoiceMemoAdapter(this, VoiceMemoCard.VIEW_MODE_EXPANDED)

        root.scroller_recent_memo.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1.01f)
                .setMinScale(0.95f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build());

        (activity as MainActivity).fab_speak_default.setOnClickListener {
            onCreateDefaultMemo()
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).getVoiceBookViewModel().getVoiceBooks().observe(this, Observer {
            if (it != null) this.onVoiceBooksLoadingSignal(it.status, it.data, it.message)
        })

        (activity as MainActivity).getVoiceBookViewModel().getRecentMemosSignal().observe(this, Observer {
            if (it != null) this.onRecentMemosLoadingSignal(it.status, it.data, it.message)
        })

        (activity as MainActivity).getVoiceBookViewModel().getVoiceMemoDeletedSignal().observe(this, Observer {
            if (it != null) this.onVoiceMemoDeletedSignal(it.status, it.data, it.message)
        })
    }

    private fun onVoiceMemoDeletedSignal(status: ResourceState, data: Int?, message: String?) {
        when(status){

            ResourceState.SUCCESS->{
                (activity as MainActivity).getVoiceBookViewModel().fetchVoiceBooks(VoiceBooksRequest())
                (activity as MainActivity).getVoiceBookViewModel().fetchRecentMemos(VoiceMemosRequest(0L))
            }

        }

    }

    private fun onRecentMemosLoadingSignal(
        status: ResourceState,
        data: List<VoiceMemo>?,
        message: String?
    ) {
        when(status){
            ResourceState.LOADING->{
                //   (activity as MainActivity).setupScreenForLoadingState()
            }
            ResourceState.SUCCESS->{
                // (activity as MainActivity).setupScreenForSuccess()
                var smallList = when(data!!.size) {
                    0-> {
                        mutableListOf(VoiceMemo(-1,-1,"","", MemoAudio("","","",""),0L))
                    }
                    in 1..5->data
                    else->data.subList(0,4)
                }

                (scroller_recent_memo.adapter as VoiceMemoAdapter).setData(smallList!!)

            }
            ResourceState.ERROR->{
                // (activity as MainActivity).setupScreenForError(message)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).getVoiceBookViewModel().fetchVoiceBooks(VoiceBooksRequest())
        (activity as MainActivity).getVoiceBookViewModel().fetchRecentMemos(VoiceMemosRequest(0L))
    }

    private fun onVoiceBooksLoadingSignal(
        status: ResourceState,
        data: List<VoiceBook>?,
        message: String?
    ) {
        when(status){
            ResourceState.LOADING->{
             //   (activity as MainActivity).setupScreenForLoadingState()
            }
            ResourceState.SUCCESS->{
               // (activity as MainActivity).setupScreenForSuccess()
                (list_books.adapter as VoiceBookAdapter).setData(data!!)

                data.forEach {
                    if(it.id == AppPref.getDefaultBookId(activity!!)){
                        (activity as MainActivity).fab_speak_default.setBackgroundTintList(
                            ColorStateList.valueOf(it.color));
                    }
                }
            }
            ResourceState.ERROR->{
               // (activity as MainActivity).setupScreenForError(message)
            }
        }
    }


}