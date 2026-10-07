package com.example.ui.components

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.localization.AppLanguage
import com.example.localization.LanguageProvider
import com.example.ui.theme.SacredAmberRadiance
import com.example.ui.theme.SacredGoldGlow

val GOAL_PRESETS = listOf(1, 4, 7, 11, 16, 21, 32, 64, 108)

@Composable
fun CustomDailyGoalDialog(
    currentTarget: Int,
    language: AppLanguage = AppLanguage.HINDI,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val strings = remember(language) { LanguageProvider.getStrings(language) }
    var targetMalas by remember { mutableIntStateOf(currentTarget) }
    var textInput by remember { mutableStateOf(currentTarget.toString()) }

    Dialog(onDismissRequest = onDismiss) {
        val isDark = MaterialTheme.colorScheme.background.red < 0.2f
        val isLiquid = MaterialTheme.colorScheme.background == com.example.ui.theme.LiquidGlassBackground

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(
                    when {
                        isLiquid -> Color(0xF20F1626)
                        isDark -> Color(0xF21F140D)
                        else -> Color(0xF7FFFDF8)
                    }
                )
                .border(
                    width = 1.2.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = if (isLiquid) 0.90f else if (isDark) 0.35f else 0.85f),
                            if (isLiquid) Color(0xCC48CAE4) else SacredGoldGlow.copy(alpha = 0.40f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(22.dp)
                .testTag("custom_daily_goal_dialog")
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Icon
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Flag,
                        contentDescription = "Sankalpa Goal",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = strings.dailyGoalTitle,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "${strings.sankalpaTarget} (${strings.malas})",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Interactive Quick Presets Row with Liquid Glass Chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(GOAL_PRESETS) { preset ->
                        val isSelected = targetMalas == preset
                        LiquidGlassChip(
                            selected = isSelected,
                            onClick = {
                                targetMalas = preset
                                textInput = preset.toString()
                            },
                            label = { Text("$preset", fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Custom Stepper & Display with Liquid Glass Icon Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // -5
                    Box(
                        modifier = Modifier
                            .liquidClickable {
                                val newTarget = (targetMalas - 5).coerceAtLeast(1)
                                targetMalas = newTarget
                                textInput = newTarget.toString()
                            }
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f))
                            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("-5", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 13.sp)
                    }

                    // -1
                    LiquidGlassIconButton(
                        icon = Icons.Default.Remove,
                        contentDescription = "Decrease",
                        onClick = {
                            val newTarget = (targetMalas - 1).coerceAtLeast(1)
                            targetMalas = newTarget
                            textInput = newTarget.toString()
                        },
                        size = 38.dp
                    )

                    // Center Number with Total Bead Calculation
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$targetMalas ${strings.malas}",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = "= ${targetMalas * 108} ${strings.beads}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    // +1
                    LiquidGlassIconButton(
                        icon = Icons.Default.Add,
                        contentDescription = "Increase",
                        onClick = {
                            val newTarget = (targetMalas + 1).coerceAtMost(1008)
                            targetMalas = newTarget
                            textInput = newTarget.toString()
                        },
                        size = 38.dp
                    )

                    // +5
                    Box(
                        modifier = Modifier
                            .liquidClickable {
                                val newTarget = (targetMalas + 5).coerceAtMost(1008)
                                targetMalas = newTarget
                                textInput = newTarget.toString()
                            }
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f))
                            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("+5", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Direct numeric input for exact count
                OutlinedTextField(
                    value = textInput,
                    onValueChange = { input ->
                        val digits = input.filter { it.isDigit() }
                        textInput = digits
                        val parsed = digits.toIntOrNull()
                        if (parsed != null && parsed > 0) {
                            targetMalas = parsed.coerceIn(1, 1008)
                        }
                    },
                    label = { Text("${strings.customGoal} (${strings.malas})") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("custom_goal_input")
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Action Buttons with Liquid Glass Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    LiquidGlassButton(
                        onClick = onDismiss,
                        primary = false,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(strings.cancel, fontWeight = FontWeight.SemiBold)
                    }

                    LiquidGlassButton(
                        onClick = {
                            onConfirm(targetMalas)
                            onDismiss()
                        },
                        primary = true,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("save_custom_goal_button")
                    ) {
                        Text(strings.submit, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}
