package com.jaustinmiles.animationplayground

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.jaustinmiles.animationplayground.model.Bubble
import com.jaustinmiles.animationplayground.model.Task
import org.jbox2d.dynamics.World
import kotlin.random.Random

class BubbleManager {

    companion object {
        fun createBubbles(context: Context, width: Float, height: Float, world: World): ArrayList<Bubble> {
            val bubbles = arrayListOf<Bubble>()
            for (i in 0 until NUM_BUBBLES) {
                val radius = Random.nextInt(150) + 50
                var bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.bubble)
                bitmap = Bitmap.createScaledBitmap(
                    bitmap,
                    radius * 2,
                    radius * 2,
                    true
                )
                val x = Random.nextInt((width - radius).toInt())
                val y = Random.nextInt((height - 200).toInt()) + 500
                val bubble =
                    Bubble(context, world, x.toFloat(), y.toFloat(), radius)
                bubble.setBmp(bitmap)
                bubbles.add(bubble)
            }
            return bubbles
        }

        fun createBubblesFromTasks(context: Context, width: Float, height: Float, world: World, tasks: List<Task>)
                : ArrayList<Bubble> {
            val bubbles = arrayListOf<Bubble>()
            for (task in tasks) {
                val (text: String, length: Int) = partitionText(task, task.taskName ?: "")
//                val radius = getRadius(context, text, length)
                val radius = when (task.priority) {
                    5 -> RADIUS_URGENT
                    4 -> RADIUS_ESSENTIAL
                    3 -> RADIUS_IMPORTANT
                    2 -> RADIUS_TRIVIAL
                    else -> RADIUS_DEFAULT
                }
                val bubble = getBubble(width, radius, height, context, world, task, text)
                configureBubble(context, radius, bubble)
                bubbles.add(bubble)
            }
            return bubbles
        }

        private fun configureBubble(context: Context, radius: Int, bubble: Bubble) {
            var bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.bubble)
            bitmap = Bitmap.createScaledBitmap(
                bitmap,
                radius * 2,
                radius * 2,
                true
            )
            bubble.setBmp(bitmap)
            bubble.textAlignment = View.TEXT_ALIGNMENT_CENTER
            bubble.gravity = Gravity.CENTER_HORIZONTAL
            bubble.gravity = Gravity.CENTER_VERTICAL
        }

        private fun getBubble(width: Float, radius: Int, height: Float, context: Context, world: World, task: Task,
                              text: String): Bubble {
            val x = Random.nextInt((width - radius).toInt())
            val y = Random.nextInt((height - radius - 200).toInt()) + 200
            val bubble = Bubble(context, world, x.toFloat(), y.toFloat(), radius)
            bubble.setTaskId(task.id)
            bubble.text = text
            return bubble
        }

        private fun getRadius(context: Context, text: String, length: Int): Int {
            val textView = TextView(context)
            textView.text = text
            val bounds = Rect()
            textView.paint.getTextBounds(textView.text.toString(), 0, length, bounds)
            return bounds.width() / 2 + 20
        }

        private fun partitionText(task: Task, taskName: String): Pair<String, Int> {
            val text: String
            val length: Int
            if (task.taskName!!.length > 20) {
                val firstHalf = taskName.subSequence(0, 19)
                val secondHalf = taskName.subSequence(19, taskName.length)
                text = String.format("%s-\n%s", firstHalf, secondHalf)
                length = 20
            } else {
                text = taskName
                length = taskName.length
            }
            return Pair(text, length)
        }
    }
}