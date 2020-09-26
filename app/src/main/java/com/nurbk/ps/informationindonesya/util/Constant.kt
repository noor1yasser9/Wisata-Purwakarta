package com.nurbk.ps.informationindonesya.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nurbk.ps.informationindonesya.R

object Constant {

    const val BASE_URL = "https://dev.farizdotid.com/api/"
    const val DETAILS = "details"
    const val TYPE = "TYPE"

    fun setImage(context: Context, url: Any?, iv: ImageView, ivPaceHolder: Int) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .placeholder(ivPaceHolder)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(iv)
    }

    var dialog: Dialog? = null
    fun showDialog(activity: Activity) {
        dialog = Dialog(activity).apply {
            setContentView(R.layout.dialog_loading)
            setCancelable(false)
        }
        dialog!!.show()
    }

}