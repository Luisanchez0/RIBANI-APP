@file:SuppressLint("MissingPermission", "UseFullScreenIntent")

package com.ribani.app.sensors

import android.annotation.SuppressLint
import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.pm.PackageManager
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.ribani.app.FallAlertActivity
import com.ribani.app.R

class RibaniMonitoringService : Service() {
    private lateinit var sensorManager: RibaniSensorManager

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        sensorManager = RibaniSensorManager(applicationContext) {
            showFallAlert()
        }
        startForeground(NOTIFICATION_ID, monitoringNotification())
        sensorManager.start()
    }

    override fun onDestroy() {
        sensorManager.stop()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int =
        START_STICKY

    override fun onBind(intent: Intent?): IBinder? = null

    private fun showFallAlert() {
        val intent = Intent(this, FallAlertActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_SINGLE_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            FALL_ALERT_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, ALERT_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Posible caída detectada")
            .setContentText("¿Te encuentras bien?")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .setFullScreenIntent(pendingIntent, true)
            .build()


        if (canPostNotifications()) {
            NotificationManagerCompat.from(this).notify(ALERT_NOTIFICATION_ID, notification)
        }
    }

    private fun monitoringNotification(): Notification =
        NotificationCompat.Builder(this, MONITORING_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("RIBANI activo")
            .setContentText("Monitoreando sensores de seguridad")
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .build()

    private fun createNotificationChannel() {
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(
            NotificationChannel(
                MONITORING_CHANNEL_ID,
                "Monitoreo de RIBANI",
                NotificationManager.IMPORTANCE_LOW
            )
        )
        manager.createNotificationChannel(
            NotificationChannel(
                ALERT_CHANNEL_ID,
                "Alertas de emergencia",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setBypassDnd(true)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
        )
    }

    private fun canPostNotifications(): Boolean =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

    private companion object {
        const val MONITORING_CHANNEL_ID = "ribani_monitoring"
        const val ALERT_CHANNEL_ID = "ribani_emergency"
        const val NOTIFICATION_ID = 100
        const val ALERT_NOTIFICATION_ID = 101
        const val FALL_ALERT_REQUEST_CODE = 102
    }
}
