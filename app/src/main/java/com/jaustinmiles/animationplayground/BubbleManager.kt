package com.jaustinmiles.animationplayground

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.Gravity
import android.view.View
import com.jaustinmiles.animationplayground.model.Bubble
import com.jaustinmiles.animationplayground.model.Task
import org.jbox2d.dynamics.World
import kotlin.random.Random

class BubbleManager {

    companion object {
        fun createBubbles(context: Context, width: Float, height: Float, world: World): ArrayList<Bubble> {
            val tasks = arrayListOf<Task>()
            for (i in 0 until NUM_BUBBLES) {
                val randLength = Random.nextInt(20) + 5
                val randName = generateString(Random, randLength)
                tasks.add(Task(null, randName, null, null, null,
                    Random.nextInt(6)))
            }
            return createBubblesFromTasks(context, width, height, world, tasks)
        }

        fun createBubblesFromTasks(context: Context, width: Float, height: Float, world: World, tasks: List<Task>)
                : ArrayList<Bubble> {
            val bubbles = arrayListOf<Bubble>()
            for (task in tasks) {
                val (text: String, _: Int) = partitionText(task, task.taskName ?: "")
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

        private fun partitionText(task: Task, taskName: String): Pair<String, Int> {
            val text: String
            val length: Int
            if (task.taskName!!.length > 15) {
                val firstHalf = taskName.subSequence(0, 14)
                val secondHalf = taskName.subSequence(14, taskName.length)
                text = String.format("%s-\n%s", firstHalf, secondHalf)
                length = 14
            } else {
                text = taskName
                length = taskName.length
            }
            return Pair(text, length)
        }

        private fun generateString(rng: Random, length: Int): String {
            val characters = charArrayOf('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p',' ')
            val text = CharArray(length)
            for (i in 0 until length) {
                text[i] = characters[rng.nextInt(characters.size)]
            }
            return String(text)
        }
    }
}