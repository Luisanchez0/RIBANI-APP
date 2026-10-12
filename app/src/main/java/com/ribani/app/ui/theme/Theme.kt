package com.ribani.app.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ribani.app.ui.theme.FondoApp
import com.ribani.app.ui.theme.Rojo
import com.ribani.app.ui.theme.TextoPrincipal
import com.ribani.app.ui.theme.VerdeClaro
import com.ribani.app.ui.theme.VerdeEstadoOk
import com.ribani.app.ui.theme.VerdeRIBANI

private val LightColorScheme = lightColorScheme(
    primary = VerdeRIBANI,
    onPrimary = Color.White,
    secondary = VerdeClaro,
    tertiary = VerdeEstadoOk,
    error = Rojo,
    background = FondoApp,
    onBackground = TextoPrincipal,
    surface = Color.White,
    onSurface = TextoPrincipal
)

@Composable
fun RIBANITheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme // RIBANI: modo claro con paleta de marca

    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
}