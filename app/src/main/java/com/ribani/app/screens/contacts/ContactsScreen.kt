package com.ribani.app.screens.contacts

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
import androidx.compose.material.icons.filled.Person
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
fun ContactsScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 28.dp, vertical = 22.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(bottom = 30.dp)
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(72.dp).padding(top = 8.dp),
                tint = Color(0xFF211F24)
            )
            Text(
                "Mis Contactos\nde Emergencia",
                fontSize = 35.sp,
                lineHeight = 42.sp,
                modifier = Modifier.padding(start = 24.dp)
            )
        }
        ContactCard("Hija Toñi", "961 354 2465")
        Spacer(Modifier.height(18.dp))
        ContactCard("Amor ❤️", "982 532 6421")
        Spacer(Modifier.height(18.dp))
        ContactCard("Hijo Juanito", "81 2456-2343")
        Spacer(Modifier.height(18.dp))
        ContactCard("Hermano Julio", "919 234 6342")
    }
}

@Composable
private fun ContactCard(name: String, phone: String) {
    Row(
        modifier = Modifier.fillMaxWidth().height(108.dp)
            .background(Color(0xFFE7E7E7), RoundedCornerShape(26.dp))
            .padding(horizontal = 26.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier.size(76.dp).background(Color(0xFF616161), CircleShape).padding(18.dp),
            tint = Color(0xFF211F24)
        )
        Column(modifier = Modifier.padding(start = 28.dp)) {
            Text(name, fontSize = 28.sp)
            Text(phone, fontSize = 21.sp, color = Color(0xFF333333))
        }
    }
}
