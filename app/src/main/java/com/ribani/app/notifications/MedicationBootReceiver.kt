package com.ribani.app.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ribani.app.data.repository.MedicationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class MedicationBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        if (intent?.action != Intent.ACTION_BOOT_COMPLETED &&
            intent?.action != "android.intent.action.LOCKED_BOOT_COMPLETED"
        ) {
            return
        }

        val appContext = context.applicationContext
        val resultado = goAsync()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            try {
                MedicationRepository.refrescar(appContext)
                // refrescar() ya llama a MedicationAlarmScheduler.programarPendientes
            } finally {
                resultado.finish()
            }
        }
    }
}
