package com.jaustinmiles.animationplayground

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import com.jaustinmiles.animationplayground.model.Bubble
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var worldManager : WorldManager
    private lateinit var animator: ValueAnimator

    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private var bubbles = arrayListOf<Bubble>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        createWorldOnLayout()

        fab.setOnClickListener{
            val intent = Intent(this, TaskFormActivity::class.java)
            startActivityForResult(intent, CREATE_NEW_TASK_CODE)
        }

        fab.setOnLongClickListener { createBubblesOnLongClick() }

        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    private fun createBubblesOnLongClick(): Boolean {
        val (height, width) = getWidthAndHeight()
        val newBubbles = BubbleManager.createBubbles(this, width.toFloat(), height.toFloat(), worldManager.world)
        for (bubble in newBubbles) bubbles.add(bubble)
        startSimulation()
        return true
    }

    private fun createWorldOnLayout() {
        val vto = canvas.viewTreeObserver
        vto.addOnGlobalLayoutListener {
            val (height, width) = getWidthAndHeight()
            if (::worldManager.isInitialized.not()) worldManager = createWorld(height, width)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CREATE_NEW_TASK_CODE -> {
                    val taskName = data?.getStringExtra(TASK_NAME)
                    val (height, width) = getWidthAndHeight()
                    val bubble = BubbleManager.createBubble(this, width.toFloat(), height.toFloat(), worldManager.world, taskName)
                    bubbles.add(bubble)
                    canvas.addView(bubble)
                    startSimulation()
                }
            }
        }
    }

    private fun startSimulation() {
        if (::animator.isInitialized && animator.isRunning) animator.cancel()
        if (bubbles.size != 0) for (bubble in bubbles) canvas.removeView(bubble)
        for (bubble in bubbles) canvas.addView(bubble)
        animator = ValueAnimator
            .ofFloat(0f, 1000f)
            .setDuration(100000)
        animator.addUpdateListener { performWorldStep(worldManager) }
        animator.start()
    }

    private fun performWorldStep(worldManager: WorldManager) {
        worldManager.world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
        for (bubble in bubbles) {
            bubble.x = bubble.worldX
            bubble.y = bubble.worldY
            bubble.rotate()
        }
    }

    private fun createWorld(height: Int, width: Int): WorldManager {
        val toolbarHeight = toolbar.height
        return WorldManager(width.toFloat(), height.toFloat(), toolbarHeight.toFloat())
    }

    private fun getWidthAndHeight(): Pair<Int, Int> {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        return Pair(height, width)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[2]
            if (::worldManager.isInitialized) worldManager.setGravity(-x * 20, y * 20)
        }
    }
}
