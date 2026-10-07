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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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

/**
 * Ultra-Glossy Glassmorphic Exit Confirmation Dialog
 * Safely guards devotees against accidental exits during sacred mantra chanting,
 * while displaying current sadhana progress and reassuring that counts are safely preserved.
 */
@Composable
fun ExitConfirmDialog(
    currentMantra: String,
    currentBead: Int,
    todayMalas: Int,
    language: com.example.localization.AppLanguage = com.example.localization.AppLanguage.HINDI,
    onContinue: () -> Unit,
    onConfirmExit: () -> Unit
) {
    val strings = com.example.localization.LanguageProvider.getStrings(language)
    Dialog(onDismissRequest = onContinue) {
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
                                Color(0x38FFFFFF),
                                Color(0x30193354),
                                Color(0x3B0F223D)
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
                                Color(0xEEF9F1E6),
                                Color(0xEEF3E5D3)
                            )
                        )
                    }
                )
                .border(
                    width = 1.4.dp,
                    brush = when {
                        isLiquid -> Brush.linearGradient(
                            listOf(
                                Color.White.copy(alpha = 0.95f),
                                Color(0xDD48CAE4),
                                Color(0x99C77DFF),
                                Color(0xCCFFD166),
                                Color.White.copy(alpha = 0.35f)
                            )
                        )
                        isDark -> Brush.verticalGradient(
                            listOf(
                                Color.White.copy(alpha = 0.40f),
                                SacredGoldGlow.copy(alpha = 0.50f),
                                Color.White.copy(alpha = 0.10f)
                            )
                        )
                        else -> Brush.verticalGradient(
                            listOf(
                                Color.White.copy(alpha = 0.95f),
                                SaffronPrimary.copy(alpha = 0.45f),
                                Color.White.copy(alpha = 0.40f)
                            )
                        )
                    },
                    shape = RoundedCornerShape(26.dp)
                )
                .padding(24.dp)
                .testTag("exit_confirm_dialog")
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Sacred Glowing Aura Icon
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                isLiquid -> Brush.radialGradient(
                                    listOf(
                                        Color(0x6600B4D8),
                                        Color(0x3348CAE4),
                                        Color.Transparent
                                    )
                                )
                                isDark -> Brush.radialGradient(
                                    listOf(
                                        SacredAmberRadiance.copy(alpha = 0.50f),
                                        Color.Transparent
                                    )
                                )
                                else -> Brush.radialGradient(
                                    listOf(
                                        SaffronPrimary.copy(alpha = 0.25f),
                                        Color.Transparent
                                    )
                                )
                            }
                        )
                        .border(
                            width = 1.2.dp,
                            brush = Brush.linearGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.90f),
                                    if (isLiquid) Color(0xFFFFD166) else SacredAmberRadiance,
                                    Color.White.copy(alpha = 0.25f)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SelfImprovement,
                        contentDescription = "Sadhana Icon",
                        tint = when {
                            isLiquid -> Color(0xFFFFD166)
                            isDark -> SacredGoldGlow
                            else -> SaffronPrimary
                        },
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = strings.exitTitle,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = when {
                            isLiquid -> Color.White
                            isDark -> Color.White
                            else -> Color(0xFF1E1B18)
                        }
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Reassuring explanation: Sadhana is safely saved
                Text(
                    text = strings.exitMessage,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 13.sp,
                        color = when {
                            isLiquid -> Color(0xFFE2E8F0)
                            isDark -> Color(0xFFD4C5B9)
                            else -> Color(0xFF6B584C)
                        }
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Sadhana Progress Capsule
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (isLiquid) Color(0x2248CAE4)
                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                        .border(
                            width = 1.dp,
                            brush = if (isLiquid) {
                                Brush.linearGradient(
                                    listOf(
                                        Color.White.copy(alpha = 0.60f),
                                        Color(0x6648CAE4),
                                        Color.White.copy(alpha = 0.15f)
                                    )
                                )
                            } else {
                                Brush.verticalGradient(
                                    listOf(Color.White.copy(alpha = 0.50f), Color.Transparent)
                                )
                            },
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = currentMantra,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isLiquid) Color(0xFFFFD166) else MaterialTheme.colorScheme.primary,
                                fontSize = 13.sp
                            ),
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${strings.todayCompletedMalas}: ",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isLiquid) Color(0xFFCBD5E1) else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Text(
                                text = "$todayMalas",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isLiquid) Color.White else MaterialTheme.colorScheme.primary
                                )
                            )
                            Text(
                                text = "  •  ${strings.bead}: ",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isLiquid) Color(0xFFCBD5E1) else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Text(
                                text = "$currentBead / 108",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isLiquid) Color(0xFF48CAE4) else MaterialTheme.colorScheme.secondary
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Action Buttons Row: Primary "Continue Chanting" and Secondary "Exit"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Secondary Button: Exit
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .liquidClickable(
                                rippleColor = Color(0xFFE53E3E),
                                onClick = onConfirmExit
                            )
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (isLiquid) Color(0x28FFFFFF)
                                else if (isDark) Color(0x353D2415)
                                else Color(0x20000000)
                            )
                            .border(
                                width = 1.dp,
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color.White.copy(alpha = 0.35f),
                                        Color.White.copy(alpha = 0.08f)
                                    )
                                ),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(vertical = 12.dp)
                            .testTag("exit_dialog_confirm_exit_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Exit",
                                tint = if (isLiquid || isDark) Color(0xFFCBD5E1) else Color(0xFF6B584C),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = strings.confirmExit,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp,
                                    color = if (isLiquid || isDark) Color(0xFFCBD5E1) else Color(0xFF6B584C)
                                )
                            )
                        }
                    }

                    // Primary Button: Continue Chanting
                    Box(
                        modifier = Modifier
                            .weight(1.3f)
                            .shadow(
                                elevation = 5.dp,
                                shape = RoundedCornerShape(16.dp),
                                spotColor = SacredAmberRadiance.copy(alpha = 0.45f)
                            )
                            .liquidClickable(
                                rippleColor = if (isLiquid) Color(0xFFFFD166) else SacredGoldGlow,
                                onClick = onContinue
                            )
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                when {
                                    isLiquid -> Brush.linearGradient(
                                        listOf(
                                            Color(0xFF0077B6),
                                            Color(0xFF00B4D8),
                                            Color(0xFFFFD166)
                                        )
                                    )
                                    isDark -> Brush.linearGradient(
                                        listOf(
                                            Color(0xFFD84315),
                                            Color(0xFFFF8F00),
                                            Color(0xFFFFB300)
                                        )
                                    )
                                    else -> Brush.linearGradient(
                                        listOf(
                                            Color(0xFFD84315),
                                            Color(0xFFE26D14),
                                            Color(0xFFFFB300)
                                        )
                                    )
                                }
                            )
                            .border(
                                width = 1.2.dp,
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color.White.copy(alpha = 0.85f),
                                        Color(0xFFFFD54F).copy(alpha = 0.50f),
                                        Color(0x20FFA000)
                                    )
                                ),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(vertical = 12.dp)
                            .testTag("exit_dialog_continue_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Continue Chanting",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = strings.continueJap,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
