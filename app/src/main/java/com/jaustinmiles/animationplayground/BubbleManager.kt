package com.jaustinmiles.animationplayground

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.jaustinmiles.animationplayground.model.Bubble
import org.jbox2d.dynamics.World
import kotlin.random.Random

class BubbleManager {

    companion object {
        fun createBubbles(context: Context, width: Float, height: Float, world: World): ArrayList<Bubble> {
            val bubbles = arrayListOf<Bubble>()
            for (i in 0 until NUM_BUBBLES) {
                val radius = Random.nextInt(150) + 50
                var bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.bubble)
                bitmap = Bitmap.createScaledBitmap(bitmap,
                    radius * 2,
                    radius * 2,
                    true)
                val x = Random.nextInt((width - radius).toInt())
                val y = Random.nextInt((height - 200).toInt()) + 500
                val bubble =
                    Bubble(context, world, x.toFloat(), y.toFloat(), radius)
                bubble.setBmp(bitmap)
                bubbles.add(bubble)
            }
            return bubbles
        }

        fun createBubble(context: Context, width: Float, height: Float, world: World, taskName: String?): Bubble {
            var bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.bubble)
            val textView = TextView(context)
            textView.text = taskName
            val bounds = Rect()
            textView.paint.getTextBounds(textView.text.toString(), 0, textView.text.length, bounds)
            val radius = bounds.width() / 2 + 20
            val x = Random.nextInt((width - radius).toInt())
            val y = Random.nextInt((height - 200).toInt()) + 500
            val bubble =
                Bubble(context, world, x.toFloat(), y.toFloat(), radius)
            bubble.text = taskName
            bitmap = Bitmap.createScaledBitmap(bitmap,
                radius * 2,
                radius * 2,
                true)
            bubble.setBmp(bitmap)
            bubble.textAlignment = View.TEXT_ALIGNMENT_CENTER
            bubble.gravity = Gravity.CENTER_HORIZONTAL
            bubble.gravity = Gravity.CENTER_VERTICAL
            return bubble
        }
    }
}