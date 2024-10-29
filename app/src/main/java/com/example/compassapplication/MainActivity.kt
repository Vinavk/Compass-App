package com.example.compassapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.example.compassapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding
    private var sensor: Sensor? = null
    private var sensorManger: SensorManager? = null
    var currentdegree = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManger = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManger!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)


    }

    override fun onSensorChanged(event: SensorEvent?) {

        var degree = Math.round(event!!.values[0])
        binding.degree.text = degree.toString()
        var animation = RotateAnimation(
            currentdegree,
            (-degree).toFloat(),
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.duration = 210
        animation.fillAfter = true

        binding.comapsImg.startAnimation(animation)
        currentdegree = (-degree).toFloat()
    }

    override fun onResume() {
        super.onResume()
        sensorManger!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManger!!.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}