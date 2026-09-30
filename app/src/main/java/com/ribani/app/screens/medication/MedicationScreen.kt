package com.ribani.app.screens.medication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.LocalPharmacy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ribani.app.data.repository.MedicationOccurrence
import com.ribani.app.data.repository.MedicationUiState

@Composable
fun MedicationScreen(
    uiState: MedicationUiState,
    onOpenDetail: (String) -> Unit,
    onOpenHistory: () -> Unit,
    onTaken: (String) -> Unit,
    onPostpone: (String) -> Unit,
    onOmit: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.LocalPharmacy,
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    tint = Color(0xFF211F24)
                )
                Text(
                    text = "Medicamentos",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            IconButton(onClick = onOpenHistory) {
                Icon(
                    Icons.Default.History,
                    contentDescription = "Historial",
                    modifier = Modifier.size(36.dp),
                    tint = Color(0xFF211F24)
                )
            }
        }

        Text(
            text = "Pendientes de Hoy",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (uiState.pendingMedications.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "¡Al día! No tienes medicamentos pendientes por ahora.",
                        fontSize = 18.sp,
                        color = Color(0xFF2E7D32)
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(uiState.pendingMedications) { occurrence ->
                    MedicationCard(
                        occurrence = occurrence,
                        onClick = { onOpenDetail(occurrence.medicationId) },
                        onTaken = { onTaken(occurrence.id) },
                        onPostpone = { onPostpone(occurrence.id) },
                        onOmit = { onOmit(occurrence.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun MedicationCard(
    occurrence: MedicationOccurrence,
    onClick: () -> Unit,
    onTaken: () -> Unit,
    onPostpone: () -> Unit,
    onOmit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFECECFD))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = occurrence.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF151515)
                    )
                    Text(
                        text = "${occurrence.dosage} • ${occurrence.time}",
                        fontSize = 18.sp,
                        color = Color(0xFF444444)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onTaken,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Tomar", fontSize = 16.sp, color = Color.White)
                }

                OutlinedButton(
                    onClick = onPostpone,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Posponer", fontSize = 16.sp, color = Color.DarkGray)
                }

                OutlinedButton(
                    onClick = onOmit,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Omitir", fontSize = 16.sp, color = Color(0xFFC62828))
                }
            }
        }
    }
}
