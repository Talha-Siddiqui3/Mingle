package com.mingle.mingle.customViews

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.mingle.mingle.R
import kotlinx.coroutines.launch
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil


class CustomLoadingView(
    context: Context,
    private val blurView: BlurView
) : RelativeLayout(context) {
    private var bottomPos = 0f
    private var centerPos = 0f
    private var animator: ObjectAnimator? = null
    private var sizeLoaded = MutableLiveData<Boolean>()

    init {
        sizeLoaded.value = false
        initializeView()
    }

    private fun initializeView() {
        View.inflate(context, R.layout.custom_loading_view_layout, this)
        this.elevation = 85f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val parentHeight = (this.parent as View).height
//        parentWidth = (this.parent as View).width
        bottomPos = parentHeight.toFloat()
        centerPos = (parentHeight / 2).toFloat() - (this.height / 2)
        this.visibility = View.GONE
        sizeLoaded.value = true
    }

    fun showLoadingView() {
        (context as AppCompatActivity).lifecycleScope.launch {
            if (sizeLoaded.value == true) {
                showLoadedView()
            } else {
                sizeLoaded.observe(context as AppCompatActivity) {
                    if (sizeLoaded.value == true) {
                        showLoadedView()
                    }
                }
            }
        }
    }

    private fun showLoadedView() {
        UIUtil.hideKeyboard(context as Activity)
        Handler(Looper.getMainLooper()).postDelayed({
           // centerInParentView()
            blurView.visibility = View.VISIBLE
            this.y = bottomPos
            this.visibility = View.VISIBLE
            animator = ObjectAnimator.ofFloat(this, "Y", centerPos)
            animator?.duration = 400
            animator?.start()
            animator?.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    startZoomAnimation()
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {


                }

            })
        }, 200)
    }

//    private fun centerInParentView() {
//        val params = this.layoutParams as LayoutParams
//        params.addRule(CENTER_IN_PARENT, TRUE)
//        this.layoutParams = params
//    }

    private fun startZoomAnimation() {

        val pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.8f, 1.2f)
        val pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.8f, 1.2f)
        val loadingView = this.rootView.findViewById<ImageView>(R.id.loadingViewImage)
        animator = ObjectAnimator.ofPropertyValuesHolder(loadingView, pvhX, pvhY)
        animator?.duration = 1000
        animator?.repeatCount = ValueAnimator.INFINITE
        animator?.repeatMode = ValueAnimator.REVERSE
        animator?.interpolator = LinearInterpolator()
        animator?.start()
    }

    fun hideLoadingView(hideBlurView: Boolean = true) {
        animator?.cancel()
        animator = ObjectAnimator.ofFloat(this, "Y", bottomPos)
        animator?.duration = 400
        animator?.start()
        animator?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                this@CustomLoadingView.visibility = View.GONE
                if (hideBlurView) {
                    blurView.visibility = View.GONE
                }

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {


            }

        })
    }

}