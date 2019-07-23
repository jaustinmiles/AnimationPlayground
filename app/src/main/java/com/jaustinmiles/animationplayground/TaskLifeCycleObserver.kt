package com.jaustinmiles.animationplayground

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.hardware.Sensor
import android.hardware.SensorManager
import com.jaustinmiles.animationplayground.database.TaskDatabase
import org.greenrobot.eventbus.EventBus

class TaskLifeCycleObserver(private val activity: MainActivity) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateEvent() {
        activity.sensorManager.registerListener(activity,
            activity.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyEvent() {
        TaskDatabase.destroyInstance()
        EventBus.getDefault().unregister(activity)
    }


}