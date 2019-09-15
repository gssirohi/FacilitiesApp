package com.techticz.app.ui.widget.error

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.techticz.app.R
import kotlinx.android.synthetic.main.content_error.view.*

class ErrorView:RelativeLayout {
    var errorListener: ErrorListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.content_error, this)
        button_retry.setOnClickListener { errorListener?.onTryAgainClicked() }

        if(errorListener == null){
            button_retry.visibility = View.GONE
        }
    }
}