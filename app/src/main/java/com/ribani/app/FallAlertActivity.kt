package com.ribani.app

import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.ribani.app.data.repository.AlertasRepository
import com.ribani.app.screens.fall.FallAlertScreen
import com.ribani.app.ui.theme.RIBANITheme
import kotlinx.coroutines.launch

class FallAlertActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }

        setContent {
            RIBANITheme {
                FallAlertScreen(
                    onOkay = {
                        // "ESTOY BIEN": cierra la alerta CAIDA en el backend
                        // (RESUELTA); el familiar la ve como confirmada.
                        lifecycleScope.launch {
                            AlertasRepository.confirmarBien()
                        }
                        dismissAlert()
                        finish()
                    },
                    onNeedHelp = {
                        // "¡NECESITO AYUDA!": escala la CAIDA a EN_ATENCION y
                        // además crea una SOS explícita para el panel familiar.
                        lifecycleScope.launch {
                            AlertasRepository.escalarAlerta()
                            AlertasRepository.enviarAlerta(
                                context = this@FallAlertActivity,
                                tipo = "SOS",
                                titulo = "El adulto mayor pide ayuda",
                                descripcion =
                                    "Presionó ¡NECESITO AYUDA! tras una alerta de posible caída.",
                            )
                        }
                        dismissAlert()
                        finish()
                    },
                    onTiempoAgotado = {
                        // Sin confirmación: la alerta CAIDA sigue abierta en
                        // el backend (se envió al detectar la caída).
                        dismissAlert()
                        finish()
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
