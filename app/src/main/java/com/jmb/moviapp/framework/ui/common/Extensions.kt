package com.jmb.moviapp.framework.ui.common

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jmb.moviapp.R

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(R.drawable.no_image).into(this)

}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}