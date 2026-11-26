package com.example.palengke_grocery_customer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme

private val LightColorScheme = lightColorScheme(
    primary = Leaf,
    onPrimary = Surface,
    primaryContainer = Color(0xFFEAF5EE),
    onPrimaryContainer = LeafDark,
    secondary = Fresh,
    onSecondary = Surface,
    secondaryContainer = Color(0xFFE5F4EA),
    onSecondaryContainer = LeafDark,
    tertiary = AccentDark,
    onTertiary = Surface,
    background = Background,
    onBackground = OnSurface,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Border,
    error = Error,
    onError = Surface
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkBackground,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnSurface,
    secondary = DarkSecondary,
    onSecondary = DarkBackground,
    secondaryContainer = Color(0xFF2A3F2E),
    onSecondaryContainer = DarkOnSurface,
    tertiary = Accent,
    onTertiary = DarkBackground,
    background = DarkBackground,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = Color(0xFF2F4A36),
    error = Color(0xFFFF8A80),
    onError = DarkBackground
)

@Composable
fun PalengkeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = PalengkeTypography,
        content = content
    )
}