package com.mingle.mingle.extensions

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun String.showToastShort(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.showToastLong(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}