package com.ribani.app.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SensorData(
    val accelerometerX: Float = 0f,
    val accelerometerY: Float = 0f,
    val accelerometerZ: Float = 0f,
    val gyroscopeX: Float = 0f,
    val gyroscopeY: Float = 0f,
    val gyroscopeZ: Float = 0f
)

class RibaniSensorManager(
    context: Context,
    private val onPossibleFall: () -> Unit
) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val accelerometer =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val gyroscope =
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private val _sensorData = MutableStateFlow(SensorData())
    val sensorData: StateFlow<SensorData> = _sensorData.asStateFlow()

    private val _isMonitoring = MutableStateFlow(false)
    val isMonitoring: StateFlow<Boolean> = _isMonitoring.asStateFlow()

    val accelerometerAvailable: Boolean
        get() = accelerometer != null

    val gyroscopeAvailable: Boolean
        get() = gyroscope != null

    private val fallDetector = FallDetector(onPossibleFall)

    fun start() {
        if (_isMonitoring.value) return

        accelerometer?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_GAME
            )
        }

        gyroscope?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_GAME
            )
        }
        _isMonitoring.value = accelerometer != null || gyroscope != null
    }

    fun stop() {
        sensorManager.unregisterListener(this)
        _isMonitoring.value = false
        fallDetector.reset()
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (event == null) return

        val currentData = _sensorData.value

        when (event.sensor.type) {

            Sensor.TYPE_ACCELEROMETER -> {

                _sensorData.value = currentData.copy(
                    accelerometerX = event.values[0],
                    accelerometerY = event.values[1],
                    accelerometerZ = event.values[2]
                )
                fallDetector.onAccelerometerChanged(
                    event.values[0],
                    event.values[1],
                    event.values[2]
                )
            }

            Sensor.TYPE_GYROSCOPE -> {

                _sensorData.value = currentData.copy(
                    gyroscopeX = event.values[0],
                    gyroscopeY = event.values[1],
                    gyroscopeZ = event.values[2]
                )
                fallDetector.onGyroscopeChanged(
                    event.values[0],
                    event.values[1],
                    event.values[2]
                )
            }
        }
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int
    ) {
        // No necesitamos hacer nada aquí por ahora
    }
}