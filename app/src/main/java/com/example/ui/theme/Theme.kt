package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = SaffronPrimary,
    onPrimary = SaffronOnPrimary,
    primaryContainer = SaffronPrimaryContainer,
    onPrimaryContainer = SaffronOnPrimaryContainer,
    secondary = GoldSecondary,
    onSecondary = GoldOnSecondary,
    secondaryContainer = GoldSecondaryContainer,
    onSecondaryContainer = GoldOnSecondaryContainer,
    tertiary = TulsiTertiary,
    onTertiary = TulsiOnTertiary,
    tertiaryContainer = TulsiTertiaryContainer,
    onTertiaryContainer = TulsiOnTertiaryContainer,
    background = DawnBackground,
    onBackground = DawnOnBackground,
    surface = DawnSurface,
    onSurface = DawnOnSurface,
    surfaceVariant = DawnSurfaceVariant,
    onSurfaceVariant = DawnOnSurfaceVariant,
    outline = DawnSurfaceCardBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = TempleDarkPrimary,
    onPrimary = SaffronOnPrimaryContainer,
    primaryContainer = TempleDarkPrimaryContainer,
    onPrimaryContainer = SaffronPrimaryContainer,
    secondary = TempleDarkSecondary,
    onSecondary = GoldOnSecondaryContainer,
    secondaryContainer = TempleDarkSecondaryContainer,
    onSecondaryContainer = GoldSecondaryContainer,
    tertiary = TempleDarkTertiary,
    onTertiary = TulsiOnTertiaryContainer,
    background = TempleDarkBackground,
    onBackground = TempleDarkOnBackground,
    surface = TempleDarkSurface,
    onSurface = TempleDarkOnSurface,
    surfaceVariant = TempleDarkSurfaceVariant,
    onSurfaceVariant = TempleDarkOnSurfaceVariant,
    outline = TempleDarkSurfaceVariant
)

private val LiquidGlassColorScheme = darkColorScheme(
    primary = LiquidGlassPrimary,
    onPrimary = Color(0xFF1E1400),
    primaryContainer = LiquidGlassPrimaryContainer,
    onPrimaryContainer = Color(0xFFFFF0C2),
    secondary = LiquidGlassSecondary,
    onSecondary = Color(0xFF00222B),
    secondaryContainer = LiquidGlassSecondaryContainer,
    onSecondaryContainer = Color(0xFFC7F3FD),
    tertiary = LiquidGlassTertiary,
    onTertiary = Color.White,
    background = LiquidGlassBackground,
    onBackground = LiquidGlassOnBackground,
    surface = Color(0x3314223A),
    onSurface = LiquidGlassOnSurface,
    surfaceVariant = Color(0x401E3250),
    onSurfaceVariant = LiquidGlassOnSurfaceVariant,
    outline = LiquidGlassBorder
)

@Composable
fun RadhaJapTheme(
    themeMode: String = "liquid_glass",
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeMode) {
        "liquid_glass" -> LiquidGlassColorScheme
        "dark" -> DarkColorScheme
        "light" -> LightColorScheme
        else -> if (darkTheme) DarkColorScheme else LightColorScheme
    }

    val isDarkAppearance = themeMode == "dark" || themeMode == "liquid_glass" || (themeMode == "system" && darkTheme)

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                val insetsController = WindowCompat.getInsetsController(window, view)
                insetsController.isAppearanceLightStatusBars = !isDarkAppearance
                insetsController.isAppearanceLightNavigationBars = !isDarkAppearance
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Keep backward compatibility
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    RadhaJapTheme(themeMode = if (darkTheme) "dark" else "light", content = content)
}
