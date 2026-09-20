package com.ribani.app

import android.os.Bundle
import android.content.Intent
import android.os.Build
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startForegroundService
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ribani.app.navigation.AppNavigation
import com.ribani.app.sensors.RibaniMonitoringService
import com.ribani.app.ui.theme.RIBANITheme

class MainActivity : ComponentActivity() {
    private var showFallAlert = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFallAlert = intent.action == ACTION_SHOW_FALL_ALERT ||
            savedInstanceState?.getBoolean(EXTRA_SHOW_FALL_ALERT, false) == true
        startMonitoringService()

        setContent {
            RIBANITheme {
                AppNavigation(startWithFallAlert = showFallAlert)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (intent.action == ACTION_SHOW_FALL_ALERT) {
            showFallAlert = true
            recreate()
        }
    }

    private fun startMonitoringService() {
        val serviceIntent = Intent(this, RibaniMonitoringService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                "android.permission.POST_NOTIFICATIONS"
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf("android.permission.POST_NOTIFICATIONS"), 200)
        }
    }

    companion object {
        const val ACTION_SHOW_FALL_ALERT = "com.ribani.app.SHOW_FALL_ALERT"
        private const val EXTRA_SHOW_FALL_ALERT = "show_fall_alert"
    }
}