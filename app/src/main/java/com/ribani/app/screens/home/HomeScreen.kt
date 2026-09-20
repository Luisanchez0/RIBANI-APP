package com.ribani.app.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.outlined.Shield
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    onSosClick: () -> Unit,
    isMonitoring: Boolean
) {
    val currentTime = remember { androidx.compose.runtime.mutableStateOf(LocalTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = LocalTime.now()
            delay(1_000)
        }
    }

    // Colores de RIBANI
    val backgroundColor = Color.White
    val greenColor = Color(0xFF4CAF50)
    val darkGreenColor = Color(0xFF006B3C)
    val redColor = Color(0xFFC62828)
    val lightGrayColor = Color(0xFFEEEEEE)
    val textColor = Color(0xFF151515)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {


        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(55.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Shield,
                    contentDescription = null,
                    modifier = Modifier.size(72.dp),
                    tint = Color(0xFF63B52A)
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = "RIBANI",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Text(
                        text = "Guardia de caídas",
                        fontSize = 17.sp,
                        color = Color.Black
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(35.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(greenColor)
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = if (isMonitoring) {
                        "Sistema Activado y Monitoreando"
                    } else {
                        "Sensores no disponibles"
                    },
                    fontSize = 15.sp,
                    color = textColor
                )
            }


            Spacer(
                modifier = Modifier.height(40.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = lightGrayColor,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(
                        vertical = 24.dp
                    ),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = currentTime.value.format(
                        DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())
                    ),
                    fontSize = 42.sp,
                    color = textColor
                )


                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Box(
                    modifier = Modifier
                        .size(86.dp)
                        .clip(CircleShape)
                        .background(greenColor),

                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "✓",
                        fontSize = 54.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }


                Spacer(
                    modifier = Modifier.height(15.dp)
                )


                Text(
                    text = "Todo está bien.",
                    fontSize = 27.sp,
                    color = textColor
                )

                Text(
                    text = "No hay Alertas Recientes",
                    fontSize = 25.sp,
                    color = textColor
                )
            }


            Spacer(
                modifier = Modifier.height(25.dp)
            )

            androidx.compose.material3.Button(
                onClick = onSosClick,

                modifier = Modifier.size(130.dp),

                shape = CircleShape,

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC62828)
                )
            ) {

                Text(
                    text = "SOS",
                    fontSize = 34.sp,
                    color = Color.White
                )
            }
        }


    }
}