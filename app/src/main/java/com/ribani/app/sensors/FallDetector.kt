package com.ribani.app.sensors

import kotlin.math.sqrt

class FallDetector(
    private val onPossibleFall: () -> Unit
) {
    private var freeFallAt: Long? = null
    private var impactAt: Long? = null
    private var orientationChanged = false
    private var lastDetectionAt = 0L

    fun onAccelerometerChanged(x: Float, y: Float, z: Float, now: Long = System.currentTimeMillis()) {
        val magnitude = sqrt(x * x + y * y + z * z)

        if (magnitude < FREE_FALL_THRESHOLD) {
            freeFallAt = now
        }

        val freeFall = freeFallAt
        if (freeFall != null && now - freeFall <= FREE_FALL_WINDOW_MS &&
            magnitude >= IMPACT_THRESHOLD
        ) {
            impactAt = now
            freeFallAt = null
            orientationChanged = false
        } else if (freeFall != null && now - freeFall > FREE_FALL_WINDOW_MS) {
            freeFallAt = null
        }

        evaluate(now)
    }

    fun onGyroscopeChanged(x: Float, y: Float, z: Float, now: Long = System.currentTimeMillis()) {
        val rotation = sqrt(x * x + y * y + z * z)
        if (impactAt != null && rotation >= ROTATION_THRESHOLD) {
            orientationChanged = true
        }
        evaluate(now)
    }

    private fun evaluate(now: Long) {
        val impact = impactAt ?: return
        if (orientationChanged &&
            now - impact in INACTIVITY_MIN_MS..INACTIVITY_MAX_MS &&
            now - lastDetectionAt > DETECTION_COOLDOWN_MS
        ) {
            lastDetectionAt = now
            impactAt = null
            orientationChanged = false
            onPossibleFall()
        } else if (now - impact > INACTIVITY_MAX_MS) {
            impactAt = null
            orientationChanged = false
        }
    }

    fun reset() {
        freeFallAt = null
        impactAt = null
        orientationChanged = false
    }

    private companion object {
        const val FREE_FALL_THRESHOLD = 2.5f
        const val IMPACT_THRESHOLD = 18f
        const val ROTATION_THRESHOLD = 2.5f
        const val FREE_FALL_WINDOW_MS = 1_000L
        const val INACTIVITY_MIN_MS = 500L
        const val INACTIVITY_MAX_MS = 5_000L
        const val DETECTION_COOLDOWN_MS = 30_000L
    }
}
