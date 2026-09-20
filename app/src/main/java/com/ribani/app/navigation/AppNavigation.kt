package com.ribani.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxSize
import com.ribani.app.screens.contacts.ContactsScreen
import com.ribani.app.screens.fall.FallAlertScreen
import com.ribani.app.screens.home.HomeScreen
import com.ribani.app.screens.location.LocationScreen
import com.ribani.app.screens.settings.SettingsScreen
import com.ribani.app.sensors.FallAlertEvents

@Composable
fun AppNavigation(startWithFallAlert: Boolean = false) {

    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    LaunchedEffect(navController) {
        FallAlertEvents.events.collect {
            navController.navigate("fall") {
                launchSingleTop = true
            }
        }
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            if (currentRoute != "fall") {
                BottomNavigationBar(
                    onHomeClick = { navController.navigate("home") },
                    onLocationClick = { navController.navigate("location") },
                    onContactsClick = { navController.navigate("contacts") },
                    onSettingsClick = { navController.navigate("settings") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (startWithFallAlert) "fall" else "home",
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {

            composable("home") {

                HomeScreen(
                    onSosClick = {
                        navController.navigate("fall") {
                            launchSingleTop = true
                        }
                    },
                    isMonitoring = true
                )
            }

            composable("location") {

                LocationScreen()
            }

            composable("contacts") {

                ContactsScreen()
            }

            composable("settings") {

                SettingsScreen()
            }

            composable("fall") {

                FallAlertScreen(
                    onOkay = {
                        navController.popBackStack()
                    },
                    onNeedHelp = {
                        // Posteriormente enviaremos la alerta
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    onHomeClick: () -> Unit,
    onLocationClick: () -> Unit,
    onContactsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8E5FF))
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationItem(Icons.Outlined.LocationOn, "Mi\nUbicación", onLocationClick)
        NavigationItem(Icons.Outlined.Person, "Contactos\nde Emergencia", onContactsClick)
        NavigationItem(Icons.Outlined.Home, "Inicio", onHomeClick)
        NavigationItem(Icons.Outlined.Settings, "Configuración", onSettingsClick)
    }
}

@Composable
private fun NavigationItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(72.dp),
            tint = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = text,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            color = Color.DarkGray
        )
    }
}