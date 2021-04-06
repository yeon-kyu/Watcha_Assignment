package com.yeonkyu.watchaassignment.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yeonkyu.watchaassignment.R

class GlideUtil {
    companion object {
        fun displayImageFromUrl(
            context: Context,
            url: String,
            imageView: ImageView
        ) {
            Glide.with(context)
                .load(url)
                .placeholder(R.drawable.img_placeholder)
                .into(imageView)
        }
    }
}