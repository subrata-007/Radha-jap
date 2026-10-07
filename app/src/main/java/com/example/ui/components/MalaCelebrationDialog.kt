package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.LiquidGlassBackground
import com.example.ui.theme.SacredAmberRadiance
import com.example.ui.theme.SacredGoldGlow
import com.example.ui.theme.SaffronPrimary

@Composable
fun MalaCelebrationDialog(
    malaCount: Int,
    mantraName: String = "श्री राधा",
    totalTodayMalas: Int = malaCount,
    language: com.example.localization.AppLanguage = com.example.localization.AppLanguage.HINDI,
    onDismiss: () -> Unit
) {
    val strings = com.example.localization.LanguageProvider.getStrings(language)
    Dialog(onDismissRequest = onDismiss) {
        val isLiquid = MaterialTheme.colorScheme.background == LiquidGlassBackground
        val isDark = MaterialTheme.colorScheme.background.red < 0.2f && !isLiquid

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(
                    when {
                        isLiquid -> Brush.verticalGradient(
                            listOf(
                                Color(0x35FFFFFF),
                                Color(0x2E193354),
                                Color(0x380F223D)
                            )
                        )
                        isDark -> Brush.verticalGradient(
                            listOf(
                                Color(0x30FFFFFF),
                                Color(0x33331E12),
                                Color(0x281A1009)
                            )
                        )
                        else -> Brush.verticalGradient(
                            listOf(
                                Color(0xF8FFFDF8),
                                Color(0xF0FAF2E4)
                            )
                        )
                    }
                )
                .border(
                    width = 1.5.dp,
                    brush = Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.95f),
                            if (isLiquid) Color(0xEE48CAE4) else SacredGoldGlow,
                            Color(0xEEFF70A6),
                            Color.White.copy(alpha = 0.40f)
                        )
                    ),
                    shape = RoundedCornerShape(26.dp)
                )
                .testTag("celebration_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Radiant Halo with Icon
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    SacredGoldGlow,
                                    SacredAmberRadiance,
                                    MaterialTheme.colorScheme.primaryContainer
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Celebration",
                        tint = Color.White,
                        modifier = Modifier.size(42.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = strings.celebrationTitle,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "« $mantraName »",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = strings.celebrationDesc,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Mala count badge with high contrast
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                if (isLiquid) Color(0x5548CAE4)
                                else MaterialTheme.colorScheme.primaryContainer
                            )
                            .border(
                                width = 1.dp,
                                color = if (isLiquid) Color(0x9948CAE4) else SacredGoldGlow,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .padding(horizontal = 18.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "${strings.thisMantraMala}: $malaCount",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = if (isLiquid) Color.White else MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }

                    if (totalTodayMalas > malaCount) {
                        Text(
                            text = "${strings.totalChantedToday}: $totalTodayMalas ${strings.malas}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sacred quote on Radha Naam
                Text(
                    text = strings.devotionalQuotes.firstOrNull() ?: "« राधा नाम परम सुखदाई »",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                LiquidGlassButton(
                    onClick = onDismiss,
                    primary = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("continue_jap_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = strings.acceptCelebration,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}
