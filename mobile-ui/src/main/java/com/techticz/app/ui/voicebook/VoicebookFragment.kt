package com.gssirohi.techticz.voicebook.ui.voicebook

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer

import com.gssirohi.techticz.voicebook.ui.voicememo.VoiceMemoAdapter
import kotlinx.android.synthetic.main.voicebook_fragment.*
import kotlinx.android.synthetic.main.voicebook_fragment.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.gssirohi.techticz.voicebook.ui.voicememo.VoiceMemoCard
import com.techticz.app.R
import com.techticz.app.ui.home.MainActivity
import com.techticz.data.model.MemoAudio
import com.techticz.data.model.VoiceMemo
import com.techticz.domain.model.VoiceBooksRequest
import com.techticz.domain.model.VoiceMemosRequest
import kotlinx.android.synthetic.main.voice_memo_card_layout.view.*
import kotlinx.android.synthetic.main.voicebook_activity.*
import org.buffer.android.boilerplate.presentation.data.ResourceState
import java.io.File


class VoicebookFragment : Fragment(),VoiceMemoAdapter.MemoListner {
    override fun onCreateDefaultMemo() {
        //Not required here
    }

    override fun onPlayClicked(memo: VoiceMemo, position: Int) {
        for(i in 1..list_memos.childCount){
            var view = list_memos.getChildAt(i-1)
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

            setClipData(ClipData.newRawUri("Voice", Uri.parse(memo.audio.localPath+"/"+memo.audio.fileName)));
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
        (activity as VoicebookActivity).getViewModel().deleteVoiceMemo(memo)
    }

    companion object {
        fun newInstance() = VoicebookFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.voicebook_fragment, container, false)
        view.list_memos.adapter = VoiceMemoAdapter(this,VoiceMemoCard.VIEW_MODE_COLLAPSABLE)


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (list_memos.layoutManager as GridLayoutManager).spanSizeLookup = (list_memos.adapter as VoiceMemoAdapter).spanSizeLookup


    }

    override fun onStart() {
        super.onStart()
        (activity as VoicebookActivity).getViewModel().getVoiceMemos().observe(this, Observer {
            if (it != null) this.onDataStateChanged(it.status, it.data, it.message)
        })



    }



    private fun onDataStateChanged(
        status: ResourceState,
        data: List<VoiceMemo>?,
        message: String?
    ) {
        when(status){
            ResourceState.LOADING->{
               // (activity as VoicebookActivity).setupScreenForLoadingState()
            }
            ResourceState.SUCCESS->{
               // (activity as VoicebookActivity).setupScreenForSuccess()
                if(data == null || data.size == 0){
                    list_memos.visibility = View.GONE
                    tv_no_memo_added.visibility = View.VISIBLE
                } else {
                    list_memos.visibility = View.VISIBLE
                    tv_no_memo_added.visibility = View.GONE
                    (list_memos.adapter as VoiceMemoAdapter).setData(data!!)
                }
            }
            ResourceState.ERROR->{
               // (activity as VoicebookActivity).setupScreenForError(message)
            }
        }
    }

    override fun onStop() {
        for(i in 1..list_memos.childCount){
            var view = list_memos.getChildAt(i-1)
            (view as VoiceMemoCard).memo_audio_player.releasePlayer()
        }
        super.onStop()
    }

}
