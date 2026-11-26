package com.example.palengke_grocery_customer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// The design sheet specifies a light theme ("Theme · Light" in Settings F04).
private val LightColorScheme = lightColorScheme(
    primary = Leaf,
    onPrimary = Surface,
    primaryContainer = GreenTint,
    onPrimaryContainer = LeafDeep,
    secondary = Fresh,
    onSecondary = Surface,
    secondaryContainer = GreenTint,
    onSecondaryContainer = LeafDeep,
    tertiary = Mango,
    onTertiary = Surface,
    background = Background,
    onBackground = Ink,
    surface = Surface,
    onSurface = Ink,
    surfaceVariant = GreenTint,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Border,
    outlineVariant = Border,
    error = Tomato,
    onError = Surface
)

@Composable
fun PalengkeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = PalengkeTypography,
        content = content
    )
}
