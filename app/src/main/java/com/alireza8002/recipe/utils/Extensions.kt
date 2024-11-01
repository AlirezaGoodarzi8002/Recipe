package com.alireza8002.recipe.utils

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar
import com.alireza8002.recipe.R
import com.alireza8002.recipe.RecipeApplication


fun AndroidViewModel.getString(@StringRes resId: Int) =
    getApplication<RecipeApplication>().getString(resId)

fun View.fadeIn() {
    val transition: Transition = Fade()

    TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
    visibility = View.VISIBLE
}

fun View.fadeOutWithAction(runnable: Runnable) = fadeWithAction(0f, runnable)

private fun View.fadeWithAction(alpha: Float = 1f, runnable: Runnable) =
    animate()
        .alpha(alpha)
        .setDuration(3000L)
        .withEndAction(runnable)

fun View.showErrorSnackBar(
    message: String,
    actionLabel: String = context.getString(R.string.retry),
    onRetry: () -> Unit
) {
    Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE).apply {
        setAction(actionLabel) { onRetry() }
        setActionTextColor(Color.YELLOW)
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.error_background_color))
        val textView = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)
        textView.textSize = 16f
        show()
    }
}