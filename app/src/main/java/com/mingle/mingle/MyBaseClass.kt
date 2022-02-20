package com.mingle.mingle

import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.mingle.mingle.customViews.BlurView
import com.mingle.mingle.customViews.CustomLoadingView
import com.mingle.mingle.extensions.showToastLong


abstract class MyBaseClass : AppCompatActivity() {
    private var rootLayout: ViewGroup? = null

    // private var rootCl: ConstraintLayout? = null
    private lateinit var context: Activity
    private var pb: ProgressBar? = null
    var blurView: BlurView? = null

    //private var alertBox: MyAlertBox? = null
    private var loadingView: CustomLoadingView? = null


    fun addCommonViews(
        rootRl: ViewGroup?,
        context: Activity,
        blurViewColor: Int = Color.BLACK
    ) {
        this.rootLayout = rootRl
        this.context = context
        addBlurViewBlack(blurViewColor)
        addProgressBar()
        addLoadingView()
        //addAlertBox()
    }

    fun showBlurView() {
        blurView?.visibility = View.VISIBLE
    }

    fun hideBlurView() {
        blurView?.visibility = View.GONE
    }


    private fun addLoadingView() {
        loadingView = CustomLoadingView(this, blurView!!)
        rootLayout?.addView(loadingView)
        val layoutParams =
            loadingView?.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.bottomToBottom = ConstraintSet.PARENT_ID;
        layoutParams.endToEnd = ConstraintSet.PARENT_ID;
        layoutParams.startToStart = ConstraintSet.PARENT_ID;
        layoutParams.topToTop = ConstraintSet.PARENT_ID;
    }


    private fun addBlurViewBlack(blurViewColor: Int) {
        blurView = BlurView(this, blurViewColor, dismissable = true)
        addViewMatchParent(blurView!!)
    }


//    private fun addAlertBox() {
//        alertBox = MyAlertBox(this, blurView!!)
//        rootLayout?.addView(alertBox)
//    }


    private fun addViewMatchParent(view: View) {
        view.layoutParams = ConstraintLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        rootLayout!!.addView(view)
        view.visibility = View.INVISIBLE
    }

    private fun addProgressBar() {
        pb = ProgressBar(context)
        if (rootLayout != null) {
            rootLayout?.addView(pb)
            val layoutParams =
                pb?.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.bottomToBottom = ConstraintSet.PARENT_ID;
            layoutParams.endToEnd = ConstraintSet.PARENT_ID;
            layoutParams.startToStart = ConstraintSet.PARENT_ID;
            layoutParams.topToTop = ConstraintSet.PARENT_ID;
            //layoutParams.ce(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
            pb?.layoutParams = layoutParams
        }
        pb?.elevation = 220f
        hideProgressBar()
    }

    fun showLoading() {
        loadingView?.showLoadingView()
    }

    fun hideLoading(hideBlurView: Boolean = true) {
        loadingView?.hideLoadingView(hideBlurView)
    }

    fun showProgressBar(blockUI: Boolean = true) {
        pb?.visibility = View.VISIBLE
        blurView?.visibility = View.VISIBLE
        if (blockUI) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }


    fun hideProgressBar(hideBlurView: Boolean = true) {
        pb?.visibility = View.INVISIBLE
        if (hideBlurView) {
            blurView?.visibility = View.INVISIBLE
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun showError(error: String?, customError: Boolean? = false) {
        if (error == null) {
            showUnknownError()
        } else {
            if (customError == true) {
                showCustomError(error)
            } else {
                when (error) {
                    "network" -> {
                        showNetworkError()
                    }
                    "unknown" -> {
                        showUnknownError()
                    }

                    "incorrectOtp" -> {
                        showCustomError(ErrorsAndMessages.invalidCode)
                    }
                    else -> {
                        showUnknownError()
                    }
                }
            }

        }

    }


    fun showNetworkError() {
//        Log.e("showNetworkError", context.localClassName)
//        alertBox?.alertBoxType = AlertBoxType.ERROR
//        alertBox?.alertMessage = ErrorsAndMessages.networkError
//        alertBox?.singleButtonListener = object : SingleButtonListenerCallback {
//            override fun buttonClickListener() {
//                alertBox?.hideAlertBox()
//            }
//        }
//        alertBox?.showDialog()
    }

    fun showUnknownError() {
        ErrorsAndMessages.unknownError.showToastLong(this)
//        alertBox?.alertBoxType = AlertBoxType.ERROR
//        alertBox?.alertMessage = ErrorsAndMessages.unknownError
//        alertBox?.singleButtonListener = object : SingleButtonListenerCallback {
//            override fun buttonClickListener() {
//                alertBox?.hideAlertBox()
//            }
//        }
//        alertBox?.showDialog()
    }

    fun showCustomError(customError: String) {
        customError.showToastLong(this)
//        //hideLoadingView()
//        hideProgressBar()
//        alertBox?.alertBoxType = AlertBoxType.ERROR
//        alertBox?.alertMessage = customError
//        alertBox?.singleButtonListener = object : SingleButtonListenerCallback {
//            override fun buttonClickListener() {
//                alertBox?.hideAlertBox()
//            }
//        }
//        alertBox?.showDialog()
    }

    fun showSuccessAlert() {
//        //hideLoadingView()
//        hideProgressBar()
//        alertBox?.alertBoxType = AlertBoxType.SUCCESS
//        alertBox?.alertMessage = null //As default is already success message
//        alertBox?.singleButtonListener = object : SingleButtonListenerCallback {
//            override fun buttonClickListener() {
//                alertBox?.hideAlertBox()
//            }
//        }
//        alertBox?.showDialog()
    }

//    fun showCustomConfirmation(
//        message: String,
//        leftButtonText: String? = null,
//        rightButtonText: String? = null,
//        alertBoxButtonListener: DoubleButtonListenerCallback?
//    ) {
//        Log.e("insideShowCustom", "true")
//        alertBox?.alertBoxType = AlertBoxType.ALERT
//        alertBox?.alertMessage = message
//        alertBox?.rightButtonText = rightButtonText
//        alertBox?.leftButtonText = leftButtonText
//        alertBox?.doubleButtonListener = object : DoubleButtonListenerCallback {
//            override fun button1ClickListener() {
//                alertBoxButtonListener?.button1ClickListener()
//            }
//
//            override fun button2ClickListener() {
//                alertBoxButtonListener?.button2ClickListener()
//            }
//        }
//        alertBox?.showDialog()
//    }

    fun hideAlertBox(hideBlurView: Boolean = true) {
        //alertBox?.hideAlertBox(hideBlurView)
    }

}