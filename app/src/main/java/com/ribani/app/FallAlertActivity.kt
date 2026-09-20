package com.ribani.app

import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ribani.app.screens.fall.FallAlertScreen
import com.ribani.app.ui.theme.RIBANITheme

class FallAlertActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setShowWhenLocked(true)
        setTurnScreenOn(true)

        setContent {
            RIBANITheme {
                FallAlertScreen(
                    onOkay = {
                        dismissAlert()
                        finish()
                    },
                    onNeedHelp = {
                        // La alerta permanecerá activa hasta integrarse el backend.
                    }
                )
            }
        }
    }

    private fun dismissAlert() {
        getSystemService(NotificationManager::class.java)
            .cancel(ALERT_NOTIFICATION_ID)
    }

    private companion object {
        const val ALERT_NOTIFICATION_ID = 101
    }
}
