package com.ribani.app.data.repository

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class MedicationStatus {
    PENDING, TAKEN, POSTPONED, OMITTED
}

data class MedicationOccurrence(
    val id: String,
    val medicationId: String,
    val name: String,
    val dosage: String,
    val time: String,
    val status: MedicationStatus = MedicationStatus.PENDING
)

data class Medication(
    val id: String,
    val name: String,
    val dosage: String,
    val schedule: String,
    val instructions: String = "",
    val isTaken: Boolean = false
)

data class MedicationUiState(
    val pendingMedications: List<MedicationOccurrence> = emptyList(),
    val medications: List<Medication> = emptyList(),
    val history: List<MedicationOccurrence> = emptyList()
)

object MedicationRepository {
    private val _uiState = MutableStateFlow(
        MedicationUiState(
            pendingMedications = listOf(
                MedicationOccurrence("occ_1", "med_1", "Paracetamol", "500 mg", "08:00 AM"),
                MedicationOccurrence("occ_2", "med_2", "Enalapril", "10 mg", "02:00 PM"),
                MedicationOccurrence("occ_3", "med_3", "Omeprazol", "20 mg", "08:00 PM")
            ),
            medications = listOf(
                Medication("med_1", "Paracetamol", "500 mg", "Cada 8 horas", "Tomar con abundante agua"),
                Medication("med_2", "Enalapril", "10 mg", "Cada 12 horas", "Tomar después de las comidas"),
                Medication("med_3", "Omeprazol", "20 mg", "Cada 24 horas", "Tomar en ayunas")
            ),
            history = listOf(
                MedicationOccurrence("occ_prev_1", "med_1", "Paracetamol", "500 mg", "Ayer 08:00 PM", MedicationStatus.TAKEN),
                MedicationOccurrence("occ_prev_2", "med_2", "Enalapril", "10 mg", "Ayer 08:00 AM", MedicationStatus.TAKEN)
            )
        )
    )
    val uiState: StateFlow<MedicationUiState> = _uiState.asStateFlow()

    fun initialize(context: Context) {
        // Inicialización de la gestión de medicamentos
    }

    fun markTaken(occurrenceId: String) {
        val current = _uiState.value
        val item = current.pendingMedications.find { it.id == occurrenceId } ?: return
        val updatedPending = current.pendingMedications.filter { it.id != occurrenceId }
        val updatedHistory = current.history + item.copy(status = MedicationStatus.TAKEN)
        _uiState.value = current.copy(
            pendingMedications = updatedPending,
            history = updatedHistory
        )
    }

    fun postpone(occurrenceId: String) {
        val current = _uiState.value
        val updatedPending = current.pendingMedications.map {
            if (it.id == occurrenceId) it.copy(status = MedicationStatus.POSTPONED) else it
        }
        _uiState.value = current.copy(pendingMedications = updatedPending)
    }

    fun markOmitted(occurrenceId: String) {
        val current = _uiState.value
        val item = current.pendingMedications.find { it.id == occurrenceId } ?: return
        val updatedPending = current.pendingMedications.filter { it.id != occurrenceId }
        val updatedHistory = current.history + item.copy(status = MedicationStatus.OMITTED)
        _uiState.value = current.copy(
            pendingMedications = updatedPending,
            history = updatedHistory
        )
    }
}
