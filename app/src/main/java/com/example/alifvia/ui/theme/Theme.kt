package com.example.alifvia.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    secondary = Secondary,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    tertiary = PrimaryVariant,
    onTertiary = androidx.compose.ui.graphics.Color.White,
    background = androidx.compose.ui.graphics.Color(0xFF121212),
    surface = androidx.compose.ui.graphics.Color(0xFF1E1E1E),
    onBackground = androidx.compose.ui.graphics.Color.White,
    onSurface = androidx.compose.ui.graphics.Color.White,
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFF2D2D2D),
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFFEEEEEE)
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    secondary = Secondary,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    tertiary = PrimaryVariant,
    onTertiary = androidx.compose.ui.graphics.Color.White,
    background = Background,
    surface = Surface
)

@Composable
fun JualanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}