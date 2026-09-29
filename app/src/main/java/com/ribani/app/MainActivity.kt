package com.ribani.app

import android.os.Bundle
import android.content.Intent
import android.os.Build
import android.content.pm.PackageManager
import android.Manifest
import androidx.core.content.ContextCompat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ribani.app.navigation.AppNavigation
import com.ribani.app.data.repository.MedicationRepository
import com.ribani.app.notifications.MedicationNotificationContract
import com.ribani.app.sensors.RibaniMonitoringService
import com.ribani.app.ui.theme.RIBANITheme

class MainActivity : ComponentActivity() {
    private var startWithFallAlert = false
    private var initialMedicationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resolveLaunchIntent(intent, savedInstanceState)
        MedicationRepository.initialize(applicationContext)
        startMonitoringService()

        setContent {
            RIBANITheme {
                AppNavigation(
                    startWithFallAlert = startWithFallAlert,
                    initialMedicationId = initialMedicationId
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (intent.action == ACTION_SHOW_FALL_ALERT ||
            intent.action == MedicationNotificationContract.ACTION_OPEN_MEDICATION
        ) {
            resolveLaunchIntent(intent, null)
            recreate()
        }
    }

    private fun resolveLaunchIntent(intent: Intent?, savedInstanceState: Bundle?) {
        startWithFallAlert = intent?.action == ACTION_SHOW_FALL_ALERT ||
            savedInstanceState?.getBoolean(EXTRA_SHOW_FALL_ALERT, false) == true
        initialMedicationId = if (intent?.action == MedicationNotificationContract.ACTION_OPEN_MEDICATION) {
            intent.getStringExtra(MedicationNotificationContract.EXTRA_MEDICATION_ID)
        } else {
            null
        }
    }

    private fun startMonitoringService() {
        val serviceIntent = Intent(this, RibaniMonitoringService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(this, serviceIntent)
        } else {
            startService(serviceIntent)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 200)
        }
    }

    companion object {
        const val ACTION_SHOW_FALL_ALERT = "com.ribani.app.SHOW_FALL_ALERT"
        private const val EXTRA_SHOW_FALL_ALERT = "show_fall_alert"
    }
}