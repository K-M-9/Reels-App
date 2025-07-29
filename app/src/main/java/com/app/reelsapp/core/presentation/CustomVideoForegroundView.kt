package com.app.reelsapp.core.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zegocloud.uikit.components.audiovideo.ZegoBaseAudioVideoForegroundView

@SuppressLint("ViewConstructor")
class CustomForegroundView(context: Context, userID: String) :
    ZegoBaseAudioVideoForegroundView(context, userID) {

    init {
        val container = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        }

        val imageView = ImageView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                dpToPx(context, 40),
                dpToPx(context, 40)
            ).apply {
                setMargins(dpToPx(context, 16), dpToPx(context, 16), dpToPx(context, 8), 0)
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        val textView = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, dpToPx(context, 16), dpToPx(context, 16), 0)
                gravity = android.view.Gravity.CENTER_VERTICAL
            }
            textSize = 20f
            setTextColor(android.graphics.Color.WHITE)
            setShadowLayer(2f, 1f, 1f, android.graphics.Color.BLACK)
        }

        Glide.with(context)
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuNhTZJTtkR6b-ADMhmzPvVwaLuLdz273wvQ&s")
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery)
            .circleCrop()
            .into(imageView)

        textView.text = userID
        container.addView(imageView)
        container.addView(textView)
        addView(container)
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}