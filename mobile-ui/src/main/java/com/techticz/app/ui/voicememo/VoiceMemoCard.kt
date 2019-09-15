package com.gssirohi.techticz.voicebook.ui.voicememo

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.ViewAnimationUtils

import com.techticz.app.R
import com.techticz.app.utils.AppUtils
import com.techticz.data.model.VoiceMemo

import kotlinx.android.synthetic.main.voice_memo_card_layout.view.*
import java.util.*


class VoiceMemoCard :FrameLayout{
    companion object{
        val VIEW_MODE_COLLAPSABLE = 1
        val VIEW_MODE_EXPANDED = 2
    }

    private var actionListener: MemoActionListener? = null
    private var viewMode = VIEW_MODE_COLLAPSABLE
    private lateinit var memo: VoiceMemo
    private var alphaAnimation: Animation? = null
    private var pixelDensity: Float = 0F
    private var isMenuHidden = true
    private var isPlayerHidden = true

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
            R.layout.voice_memo_card_layout,this);

        pixelDensity = getResources().getDisplayMetrics().density;
        alphaAnimation = AnimationUtils.loadAnimation(context, R.anim.alpha_anim);
        ib_memo_options.setOnClickListener { v->launchSettings(v) }
        ib_memo_audio.setOnClickListener { v->launchPlayer(v) }
        tv_memo_title.setOnClickListener{
            tv_memo_title.visibility = View.GONE
            tv_memo_description.visibility = View.VISIBLE
            control_panel.visibility = View.VISIBLE
            var listner = object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    //ib_expend.setImageResource(R.drawable.ic_keyboard_arrow_up)
                }
            }
            rotateView(ib_expend,true,listner)

        }

        b_delete.setOnClickListener {
            ib_memo_options.performClick()
            actionListener?.onDeleteClicked(memo)
        }
        b_share.setOnClickListener {
            ib_memo_options.performClick()
            actionListener?.onShareClicked(memo)
        }
        tv_memo_description.setOnClickListener{
            tv_memo_title.visibility = View.VISIBLE
            tv_memo_description.visibility = View.GONE
            control_panel.visibility = View.GONE
            var listner = object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                  //  ib_expend.setImageResource(R.drawable.ic_keyboard_arrow_down)
                }
            }
            rotateView(ib_expend,false,listner)
        }

    }

    fun setCardViewMode(mode :Int){
        viewMode = mode
        if(viewMode == VIEW_MODE_EXPANDED){
            this.layoutParams = ViewGroup.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT)
            tv_memo_title.visibility = View.GONE
            tv_memo_description.visibility = View.VISIBLE
            control_panel.visibility = View.VISIBLE
            ib_expend.visibility = View.GONE
            tv_memo_description.isClickable = false
        }
    }


    fun rotateView(v: View, rotate:Boolean,listner:AnimatorListenerAdapter):Boolean {
        v.animate().setDuration(200)
            .setListener(listner)
            .rotation(when(rotate){ true->180f else->0f});
        return rotate;
    }


    fun launchSettings(view: View) {

        /*
         MARGIN_RIGHT = 16;
         FAB_BUTTON_RADIUS = 28;
         */
        var x = tv_memo_description.getRight()
        val y = tv_memo_description.getBottom()
        x -= (28 * pixelDensity + 16 * pixelDensity).toInt()

        val hypotenuse = Math.hypot(tv_memo_description.getWidth().toDouble(), tv_memo_description.getHeight().toDouble()).toInt()

        if (isMenuHidden) {

            ib_memo_options.setBackgroundResource(R.drawable.rounded_cancel_button)
            ib_memo_options.setImageResource(R.drawable.ic_action_cancel)

            val parameters = linearView.getLayoutParams() as FrameLayout.LayoutParams
            parameters.height = tv_memo_description.getHeight()
            linearView.setLayoutParams(parameters)

            val anim =
                ViewAnimationUtils.createCircularReveal(linearView, x, y, 0f, hypotenuse.toFloat())
            anim.duration = 400

            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {
                    tv_memo_date.visibility = View.GONE
                    ib_memo_audio.visibility = View.GONE
                }

                override fun onAnimationEnd(animator: Animator) {
                    layoutButtons.setVisibility(View.VISIBLE)
                    layoutButtons.startAnimation(alphaAnimation)
                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })

            linearView.setVisibility(View.VISIBLE)
            anim.start()

            isMenuHidden = false
        } else {

            ib_memo_options.setBackgroundResource(R.drawable.rounded_button)
            ib_memo_options.setImageResource(R.drawable.ic_settings)

            val anim =
                ViewAnimationUtils.createCircularReveal(linearView, x, y, hypotenuse.toFloat(), 0f)
            anim.duration = 400

            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {

                }

                override fun onAnimationEnd(animator: Animator) {
                    linearView.setVisibility(View.GONE)
                    layoutButtons.setVisibility(View.GONE)
                    tv_memo_date.visibility = View.VISIBLE
                    ib_memo_audio.visibility = View.VISIBLE
                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })

            anim.start()
            isMenuHidden = true
        }
    }
    fun launchPlayer(view: View) {

        /*
         MARGIN_RIGHT = 16;
         FAB_BUTTON_RADIUS = 28;
         */
        var x = tv_memo_description.getRight()
        val y = tv_memo_description.getBottom()
        x -= (28 * pixelDensity + 16 * pixelDensity).toInt()

        val hypotenuse = Math.hypot(tv_memo_description.getWidth().toDouble(), tv_memo_description.getHeight().toDouble()).toInt()

        if (isPlayerHidden) {

            ib_memo_audio.setBackgroundResource(R.drawable.rounded_cancel_button)
            ib_memo_audio.setImageResource(R.drawable.ic_action_cancel)

            val parameters = linearView_audio.getLayoutParams() as FrameLayout.LayoutParams
            parameters.height = tv_memo_description.getHeight()
            linearView_audio.setLayoutParams(parameters)

            val anim =
                ViewAnimationUtils.createCircularReveal(linearView_audio, 0, y, 0f, hypotenuse.toFloat())
            anim.duration = 400

            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {
                    tv_memo_date.visibility = View.GONE
                    memo_audio_player.readyPlayer()
                    ib_memo_options.visibility = View.GONE
                }

                override fun onAnimationEnd(animator: Animator) {
                    memo_audio_player.setVisibility(View.VISIBLE)
                    memo_audio_player.startAnimation(alphaAnimation)
                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })

            linearView_audio.setVisibility(View.VISIBLE)
            anim.start()

            isPlayerHidden = false
        } else {

            ib_memo_audio.setBackgroundResource(R.drawable.rounded_button)
            ib_memo_audio.setImageResource(R.drawable.ic_volume_up)

            val anim =
                ViewAnimationUtils.createCircularReveal(linearView_audio, 0, y, hypotenuse.toFloat(), 0f)
            anim.duration = 400

            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {

                }

                override fun onAnimationEnd(animator: Animator) {
                    linearView_audio.setVisibility(View.GONE)
                    memo_audio_player.setVisibility(View.GONE)
                    memo_audio_player.releasePlayer()
                    ib_memo_options.visibility = View.VISIBLE
                    tv_memo_date.visibility = View.VISIBLE
                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })

            anim.start()
            isPlayerHidden = true
        }
    }

    fun getMemo(): VoiceMemo {
        return memo
    }

    fun bindMemo(
        memo: VoiceMemo,
        playerListner: MemoAudioPlayer.PlayerListner,
        actionListner: MemoActionListener
    ) {
        this.actionListener = actionListner
        this.memo = memo
        tv_memo_title.text = memo.title
        tv_memo_description.text = memo.desc
        memo_audio_player.bindData(memo.audio)
        memo_audio_player.setPlayerListner(playerListner)
        var cal = Calendar.getInstance()
        cal.timeInMillis = memo.timestamp
        tv_memo_date.text = AppUtils.getDisplayDate(cal)
    }

    interface MemoActionListener{
        fun onDeleteClicked(memo:VoiceMemo)
        fun onShareClicked(memo:VoiceMemo)
    }

}