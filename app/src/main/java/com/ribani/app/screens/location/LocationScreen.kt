package com.ribani.app.screens.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LocationScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.align(Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = null,
                modifier = Modifier.padding(end = 28.dp),
                tint = Color(0xFF211F24)
            )
            Text("Mi ubicacion", fontSize = 38.sp, fontWeight = FontWeight.Normal)
        }
        Column(
            modifier = Modifier.padding(top = 500.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("17/09/2027", fontSize = 30.sp, color = Color.Black)
            Text("10:45 am", fontSize = 30.sp, color = Color.Black)
        }
    }
}
