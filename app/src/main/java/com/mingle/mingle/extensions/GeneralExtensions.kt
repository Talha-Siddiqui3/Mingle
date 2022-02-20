package com.mingle.mingle.extensions

import android.util.Log
import com.mingle.mingle.BuildConfig

fun String.printLog(text: Any?) {
    //if (BuildConfig.DEBUG) {
        Log.e(this, if (text !is String) text.toString() else text)
    //}
}