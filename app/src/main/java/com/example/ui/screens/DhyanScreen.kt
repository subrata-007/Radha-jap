package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.LanguageProvider
import com.example.ui.components.LiquidGlassIconButton
import com.example.ui.components.MalaCelebrationDialog
import com.example.ui.components.fluidAmbientBackdrop
import com.example.ui.components.liquidClickable
import com.example.ui.theme.SacredAmberRadiance
import com.example.ui.theme.SacredGoldGlow
import com.example.ui.theme.TempleDarkBackground
import com.example.ui.theme.TempleDarkPrimary
import com.example.viewmodel.JapUiState

@Composable
fun DhyanScreen(
    uiState: JapUiState,
    onIncrement: () -> Unit,
    onToggleDrone: () -> Unit,
    onExit: () -> Unit,
    onDismissCelebration: () -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = remember(uiState.appLanguage) {
        LanguageProvider.getStrings(uiState.appLanguage)
    }

    // Handle back button press to return safely
    BackHandler {
        onExit()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "dhyanBreath")
    val breathScale by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dhyanScale"
    )
    val breathAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.60f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dhyanAlpha"
    )

    val isLiquid = uiState.themeMode == "liquid_glass"

    Box(
        modifier = modifier
            .fillMaxSize()
            .fluidAmbientBackdrop(uiState.themeMode)
            .liquidClickable(
                rippleColor = if (isLiquid) Color(0xFF48CAE4) else SacredGoldGlow,
                onClick = onIncrement
            )
            .testTag("dhyan_screen_tap_surface")
    ) {
        // Top Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LiquidGlassIconButton(
                icon = Icons.Default.Close,
                contentDescription = strings.exitDhyan,
                onClick = onExit,
                size = 44.dp,
                tint = Color.White,
                modifier = Modifier.testTag("exit_dhyan_button")
            )

            LiquidGlassIconButton(
                icon = Icons.Default.GraphicEq,
                contentDescription = strings.soundDrone,
                onClick = onToggleDrone,
                activeGlow = uiState.isDronePlaying,
                size = 44.dp,
                tint = if (uiState.isDronePlaying) SacredGoldGlow else Color.White.copy(alpha = 0.7f)
            )
        }

        // Center Divine Meditative Focus
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ambient Aura Halo
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .scale(breathScale)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = if (isLiquid) {
                                listOf(
                                    Color(0xFF00B4D8).copy(alpha = breathAlpha * 0.65f),
                                    Color(0xFFC77DFF).copy(alpha = breathAlpha * 0.35f),
                                    Color(0x00C77DFF)
                                )
                            } else {
                                listOf(
                                    SacredGoldGlow.copy(alpha = breathAlpha * 0.7f),
                                    SacredAmberRadiance.copy(alpha = breathAlpha * 0.4f),
                                    SacredAmberRadiance.copy(alpha = 0f)
                                )
                            }
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Inner Glass Lotus Orb
                Box(
                    modifier = Modifier
                        .size(190.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = if (isLiquid) {
                                    listOf(
                                        Color(0xE81A365D),
                                        Color(0xF2122846),
                                        Color(0xF80E1F36)
                                    )
                                } else {
                                    listOf(
                                        Color(0xE64A2D1A),
                                        Color(0xD9331E12),
                                        Color(0xCC20130B)
                                    )
                                }
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "🪷",
                            fontSize = 32.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = uiState.currentMantra.split("\n").firstOrNull() ?: "श्री राधा",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TempleDarkPrimary
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${uiState.currentBead + 1} / 108",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "${strings.totalToday}: ${uiState.todayMalas} ${strings.malas} | ${strings.sankalpaTarget}: ${uiState.sankalpaTargetMalas}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TempleDarkPrimary.copy(alpha = 0.85f),
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = strings.tapAnywherePrompt,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White.copy(alpha = 0.5f)
                )
            )
        }
    }

    // Celebration pop-up on 108 completion inside Dhyan Mode
    if (uiState.showCelebrationDialog) {
        MalaCelebrationDialog(
            malaCount = uiState.celebrationMalaCount,
            mantraName = uiState.celebrationMantraName,
            totalTodayMalas = uiState.todayMalas,
            language = uiState.appLanguage,
            onDismiss = onDismissCelebration
        )
    }
}
