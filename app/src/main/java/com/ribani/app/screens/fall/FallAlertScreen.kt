package com.ribani.app.screens.fall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FallAlertScreen(
    onOkay: () -> Unit,
    onNeedHelp: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3E0FF))
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "¡Alerta de posible\ncaída! 🚶",
            fontSize = 28.sp
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
            text = "TIEMPO: 30s",
            fontSize = 18.sp
        )
    }
}