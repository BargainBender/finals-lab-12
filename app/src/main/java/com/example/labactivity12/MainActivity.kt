package com.example.labactivity12

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var tvOutputs: TextView
    private lateinit var sensorManager: SensorManager
    private lateinit var tvRotationVectorX: TextView
    private lateinit var tvRotationVectorY: TextView
    private lateinit var tvRotationVectorZ: TextView
    private lateinit var pbX: ProgressBar
    private lateinit var pbY: ProgressBar
    private lateinit var pbZ: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvRotationVectorX = findViewById(R.id.tvRotationVectorX)
        tvRotationVectorY = findViewById(R.id.tvRotationVectorY)
        tvRotationVectorZ = findViewById(R.id.tvRotationVectorZ)
        pbX = findViewById(R.id.pbX)
        pbY = findViewById(R.id.pbY)
        pbZ = findViewById(R.id.pbZ)

        tvOutputs = findViewById(R.id.tvOutputs)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        var sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        var rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)


        for (sensor in sensors) {
            tvOutputs.append("\n${sensor.name}\n${sensor.vendor}\n${sensor.version}\n")
        }

        sensorManager.registerListener(this@MainActivity, rotationVector, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(ev: SensorEvent?) {
        if (ev != null) {
            var x = ((ev.values[0] * 100 + 100) / 2).toInt()
            var y = ((ev.values[1] * 100 + 100) / 2).toInt()
            var z = ((ev.values[2] * 100 + 100) / 2).toInt()
            tvRotationVectorX.text = x.toString()
            tvRotationVectorY.text = y.toString()
            tvRotationVectorZ.text = z.toString()
            Thread {
                kotlin.run {
                    pbX.progress = x
                    pbY.progress = y
                    pbZ.progress = z
                }
            }.start()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}