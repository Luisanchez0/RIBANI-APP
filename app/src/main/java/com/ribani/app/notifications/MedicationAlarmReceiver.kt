package com.ribani.app.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ribani.app.data.remote.ApiClient
import com.ribani.app.data.repository.MedicationRepository

/**
 * Dispara la alarma full-screen (como la alerta de caída) cuando el

 */
class MedicationAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        // Alarmas exactas se entregan aun con la app muerta (proceso nuevo).
        ApiClient.init(context.applicationContext)

        val tomaId = intent.getStringExtra(MedicationNotificationContract.EXTRA_TOMA_ID)
            ?: return
        val nombre = intent.getStringExtra(MedicationAlarmNotifications.EXTRA_NOMBRE_MEDICAMENTO)
            .orEmpty()
        val dosis = intent.getStringExtra(MedicationAlarmNotifications.EXTRA_DOSIS_MEDICAMENTO)
            .orEmpty()
        val horaProgramada = intent.getStringExtra(MedicationAlarmNotifications.EXTRA_HORA_PROGRAMADA)
            .orEmpty()
        val ventanaSeg = intent.getIntExtra(
            MedicationAlarmNotifications.EXTRA_VENTANA_SEGUNDOS, 60
        )
        val instrucciones = intent.getStringExtra(
            MedicationAlarmNotifications.EXTRA_INSTRUCCIONES_MEDICAMENTO
        )


        val state = MedicationRepository.uiState.value
        if (state.cargado) {
            val siguePendiente = state.pendingMedications.any { it.id == tomaId }
            if (!siguePendiente) return
        } else {
            MedicationRepository.initialize(context.applicationContext)
        }

        MedicationAlarmNotifications.mostrarAlarmaToma(
            context = context,
            tomaId = tomaId,
            nombre = nombre,
            dosis = dosis,
            horaProgramada = horaProgramada,
            ventanaSegundos = ventanaSeg,
            instrucciones = instrucciones,
        )
    }
}
