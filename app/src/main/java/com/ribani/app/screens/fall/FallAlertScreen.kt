package com.ribani.app.screens.fall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private const val SEGUNDOS_LIMITE = 30

/**
 * Alerta de posible caída en pantalla completa. La alerta (tipo CAIDA)
 * ya se envió al backend al detectarla; el contador informa al adulto
 * cuánto tiempo tiene para confirmar que está bien. Al vencer el tiempo
 * sin confirmación se cierra la pantalla (onTiempoAgotado) — la alerta
 * sigue abierta en el backend para el familiar.
 */
@Composable
fun FallAlertScreen(
    onOkay: () -> Unit,
    onNeedHelp: () -> Unit,
    onTiempoAgotado: () -> Unit,
) {
    var segundosRestantes by remember { mutableIntStateOf(SEGUNDOS_LIMITE) }

    // Cuenta regresiva: al llegar a 0 sin confirmación se cierra la pantalla;
    // la alerta CAIDA ya quedó abierta en el backend desde la detección.
    LaunchedEffect(Unit) {
        while (segundosRestantes > 0) {
            delay(1_000)
            segundosRestantes--
        }
        onTiempoAgotado()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3E0FF))
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Rounded.Warning,
            contentDescription = "Alerta de caída",
            modifier = Modifier.size(90.dp),
            tint = Color(0xFFC62828)
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "¡Alerta de posible\ncaída!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Text(
            text = "¿Te encuentras bien?",
            fontSize = 21.sp
        )

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        Button(
            onClick = onOkay,
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF006B3C)
            )
        ) {

            Text(
                text = "ESTOY BIEN",
                fontSize = 20.sp
            )
        }

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        Button(
            onClick = onNeedHelp,
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC62828)
            )
        ) {

            Text(
                text = "¡NECESITO AYUDA!",
                fontSize = 20.sp
            )
        }

        Spacer(
            modifier = Modifier.height(50.dp)
        )

        Text(
            text = "TIEMPO RESTANTE: ${segundosRestantes}s",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (segundosRestantes <= 10) Color(0xFFC62828) else Color(0xFF211F24)
        )
    }
}