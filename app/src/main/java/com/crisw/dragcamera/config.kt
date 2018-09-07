package com.crisw.dragcamera

import android.app.Activity
import android.content.Intent
import android.view.View

/**
 * Created by wt on 2018/9/7.
 */

//跳转UI
inline fun <reified T> View.goUI() {
    this.setOnClickListener {
        if (this.context is Activity) {
            (this.context as Activity).startActivity(Intent(this.context, T::class.java))
        }
    }

}