package com.ribani.app.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 30.dp, vertical = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Icon(Icons.Default.Settings, null, Modifier.size(72.dp), tint = Color(0xFF211F24))
            Text("Configuración", fontSize = 38.sp, modifier = Modifier.padding(start = 24.dp))
        }
        Text("Información", fontSize = 28.sp, modifier = Modifier.padding(start = 40.dp, bottom = 14.dp))
        SettingCard(Icons.Default.Lock, "Seguridad")
        Spacer(Modifier.height(18.dp))
        SettingCard(Icons.Default.Info, "Acerca de RIBANI")
        Spacer(Modifier.height(18.dp))
        SettingCard(Icons.Default.VolumeUp, "Sonido de alertas")
        Spacer(Modifier.height(48.dp))
        Text("Acciones", fontSize = 28.sp, modifier = Modifier.padding(start = 40.dp, bottom = 14.dp))
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(70.dp),
            shape = RoundedCornerShape(36.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF2020))
        ) {
            Text("Cerrar Sesión", fontSize = 27.sp, color = Color.White)
        }
    }
}

@Composable
private fun SettingCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(100.dp)
            .background(Color(0xFFE7E7E7), RoundedCornerShape(26.dp))
            .padding(horizontal = 44.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon, null,
            Modifier.size(62.dp).background(Color(0xFFD0B8FF), CircleShape).padding(17.dp),
            tint = Color(0xFF211F24)
        )
        Text(label, fontSize = 22.sp, modifier = Modifier.padding(start = 22.dp))
    }
}
