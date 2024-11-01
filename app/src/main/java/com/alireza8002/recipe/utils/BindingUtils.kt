package com.alireza8002.recipe.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.alireza8002.recipe.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(view.context)
            .load(it)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(R.drawable.bg_placeholder)
            .into(view)
    }
}

@BindingAdapter("imageResource")
fun loadImage(view: ImageView, @DrawableRes imageRes: Int?) {
    imageRes?.let {
        Glide.with(view.context)
            .load(it)
            .into(view)
    }
}