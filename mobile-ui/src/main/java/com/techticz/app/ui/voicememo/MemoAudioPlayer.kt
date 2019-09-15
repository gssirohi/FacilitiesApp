package com.gssirohi.techticz.voicebook.ui.voicememo

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.os.Environment
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.techticz.app.R
import com.techticz.data.model.MemoAudio
import kotlinx.android.synthetic.main.voice_memo_audio_player_layout.view.*
import java.io.File

import java.io.IOException

class MemoAudioPlayer:ConstraintLayout{
    private lateinit var memoAudio: MemoAudio
    var listner:PlayerListner? = null
    private var player:MediaPlayer = MediaPlayer()
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    init {
        initUi()

    }

    private fun initUi() {
        this.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val audioPlayerContent =  View.inflate(context,
            R.layout.voice_memo_audio_player_layout,this);
    }

    fun bindData(audio: MemoAudio) {
        memoAudio = audio
        tv_audio_name.text = audio.fileName
        tv_audio_time_elapsed.text = "00:00"
        tv_audio_total_time.text = "05:46"
        seekbar_audio.progress = 0
    }

    private fun initPlayer() {
        try {
            try {

                var path = memoAudio.localPath+ File.separator+memoAudio.fileName
               Log.d("Tag","Path:"+path)
                player.setDataSource(path)

            } catch (e: IOException) {
                e.printStackTrace()
            }

            player.setOnPreparedListener { mp ->
                seekbar_audio.max = mp.duration
                ib_play_pause_memo.isEnabled = true
                ib_play_pause_memo.alpha = 1f

                ib_stop_memo.isEnabled = false
                ib_stop_memo.alpha = .5f

                ib_play_pause_memo.setImageResource(R.drawable.ic_action_playback_play)
            }

            player.setOnCompletionListener { m ->
                stopAudio()
            }

            player.prepare()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setPlayerListner(listner:PlayerListner){
        this.listner = listner
    }

    val seekHandler = Handler()

    fun readyPlayer(){
        ib_play_pause_memo.isEnabled = false
        ib_play_pause_memo.alpha = 0.5f

        player = MediaPlayer()
        ib_play_pause_memo.setOnClickListener {
            if(player.isPlaying){
                pauseAudio()
            } else {
                playAudio()
            }
        }
        ib_stop_memo.setOnClickListener { stopAudio() }
        initPlayer()
    }

    fun playAudio(){
        listner?.playClicked()
        try {
                player.start()
                ib_stop_memo.isEnabled = true
                ib_stop_memo.alpha = 1f

                ib_play_pause_memo.setImageResource(R.drawable.ic_action_playback_pause)
                (context as Activity).runOnUiThread(object : Runnable {
                    override fun run() {
                        try {
                            if (player.isPlaying && player.currentPosition != player.duration) {
                                seekbar_audio.progress = player.currentPosition
                            }
                            seekHandler.postDelayed(this, 10)
                        } catch (e:java.lang.Exception){

                        }
                    }
                })

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun pauseAudio(){
        player.pause()
        ib_stop_memo.isEnabled = true
        ib_stop_memo.alpha = 1f

        ib_play_pause_memo.setImageResource(R.drawable.ic_action_playback_play)
    }

    open fun stopAudio(){
        try {
            if (player.isPlaying || player.currentPosition != 0) {
                player.pause()
                player.seekTo(0)
                seekbar_audio.progress = 0

                ib_stop_memo.isEnabled = false
                ib_stop_memo.alpha = 0.5f

                ib_play_pause_memo.isEnabled = true
                ib_play_pause_memo.alpha = 1f

                ib_play_pause_memo.setImageResource(R.drawable.ic_action_playback_play)
            }
        } catch (e:java.lang.Exception){
            e.printStackTrace()
        }
    }

    fun releasePlayer() {
        stopAudio()
        player.release()
    }

    interface PlayerListner{
        fun playClicked()
    }

}