package com.mingle.mingle.customViews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.mingle.mingle.R

class BlurView : RelativeLayout {

    // var onDismissCallback: (() -> Unit?)? = null
    val onDismissCallbacks: ArrayList<(() -> Unit?)?> = ArrayList()

    constructor(context: Context, color: Int? = Color.BLACK, dismissable: Boolean = false) : super(
        context
    ) {
        init(color, dismissable)
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(null, false)
    }

    private fun init(color: Int?, dismissable: Boolean) {
        View.inflate(context, R.layout.blur_view_layout, this)
        elevation = 50f
        setBackgroundColor(color!!)
        background.alpha = 214
        isFocusable = true
        isClickable = true

        if (dismissable) {
            this.setOnClickListener {
                for (callback in onDismissCallbacks) {
                    callback?.invoke()
                }
            }
        }

    }

    fun setOpacity(blurOpacity: Int) {
        background.alpha = blurOpacity
    }


}