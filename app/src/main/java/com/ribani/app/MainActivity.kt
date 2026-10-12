package com.ribani.app

import android.app.AlarmManager
import android.os.Bundle
import android.content.Intent
import android.os.Build
import android.content.pm.PackageManager
import android.Manifest
import androidx.core.content.ContextCompat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ribani.app.data.remote.ApiClient
import com.ribani.app.navigation.AppNavigation
import com.ribani.app.data.repository.MedicationRepository
import com.ribani.app.notifications.MedicationNotificationContract
import com.ribani.app.sensors.RibaniMonitoringService
import com.ribani.app.ui.theme.RIBANITheme

class MainActivity : ComponentActivity() {
    private var startWithFallAlert = false
    private var initialMedicationId: String? = null
    private var startWithInvitaciones = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resolveLaunchIntent(intent, savedInstanceState)
        ApiClient.init(applicationContext)
        MedicationRepository.initialize(applicationContext)
        startMonitoringService()

        setContent {
            RIBANITheme {
                AppNavigation(
                    startWithFallAlert = startWithFallAlert,
                    initialMedicationId = initialMedicationId,
                    startWithInvitaciones = startWithInvitaciones
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (intent.action == ACTION_SHOW_FALL_ALERT ||
            intent.action == MedicationNotificationContract.ACTION_OPEN_MEDICATION ||
            intent.action == Intent.ACTION_VIEW
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
        startWithInvitaciones =
            savedInstanceState?.getBoolean(EXTRA_SHOW_INVITACIONES, false) == true ||
            (intent?.action == Intent.ACTION_VIEW &&
                intent.data?.host == HOST_INVITACIONES)
    }

    private fun startMonitoringService() {
        val serviceIntent = Intent(this, RibaniMonitoringService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 200)
        }

        pedirPermisoAlarmasExactas()
    }

    /**
     * Android 12+ (crítico en 14+): sin SCHEDULE_EXACT_ALARM concedida,
     * setAlarmClock lanza SecurityException y las alarmas de medicamento
     * no llegan. Envía al usuario al diálogo del sistema para
     * "Permitir alarmas y recordatorios". Es una única concesión
     * persistente en Settings, no un requestPermissions.
     */
    private fun pedirPermisoAlarmasExactas() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return

        val alarmManager = getSystemService(AlarmManager::class.java) ?: return
        if (!alarmManager.canScheduleExactAlarms()) {
            try {
                startActivity(
                    Intent(
                        android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                        android.net.Uri.parse("package:$packageName")
                    )
                )
            } catch (e: Exception) {
                // Algunos OEMs no resuelven el intent; el fallback
                // setWindow del scheduler cubre alarma no exacta.
            }
        }
    }

    companion object {
        const val ACTION_SHOW_FALL_ALERT = "com.ribani.app.SHOW_FALL_ALERT"
        private const val EXTRA_SHOW_FALL_ALERT = "show_fall_alert"
        private const val EXTRA_SHOW_INVITACIONES = "show_invitaciones"
        const val HOST_INVITACIONES = "invitaciones"
    }
}