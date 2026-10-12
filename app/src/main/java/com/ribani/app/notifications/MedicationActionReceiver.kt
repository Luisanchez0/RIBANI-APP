package com.ribani.app.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ribani.app.data.repository.MedicationRepository


class MedicationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val tomaId = intent.getStringExtra(MedicationNotificationContract.EXTRA_TOMA_ID)
            ?: return
        val notificationId = intent.getIntExtra(
            MedicationNotificationContract.EXTRA_NOTIFICATION_ID, -1
        )

        when (intent.action) {
            MedicationNotificationContract.ACTION_MARK_TAKEN -> {
                MedicationRepository.markTaken(context, tomaId)
            }
            MedicationNotificationContract.ACTION_MARK_OMITTED -> {
                MedicationRepository.markOmitted(context, tomaId)
            }
            else -> return
        }

        if (notificationId != -1) {
            MedicationAlarmNotifications.cancelar(context, tomaId)
        }
    }
}
