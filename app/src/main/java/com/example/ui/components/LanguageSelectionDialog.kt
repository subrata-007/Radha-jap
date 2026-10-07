package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Translate
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
import com.example.localization.AppLanguage
import com.example.ui.theme.LiquidGlassBackground
import com.example.ui.theme.SacredAmberRadiance
import com.example.ui.theme.SacredGoldGlow
import com.example.ui.theme.SaffronPrimary

/**
 * Ultra-Glossy Glassmorphic Language Selector Dialog
 * Presents 16 Indian languages in their native script with regional context,
 * allowing instant devotional chanting in the user's mother tongue.
 */
@Composable
fun LanguageSelectionDialog(
    currentLanguage: AppLanguage,
    onSelectLanguage: (AppLanguage) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        val isLiquid = MaterialTheme.colorScheme.background == LiquidGlassBackground
        val isDark = MaterialTheme.colorScheme.background.red < 0.2f && !isLiquid

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(
                    when {
                        isLiquid -> Brush.verticalGradient(
                            listOf(
                                Color(0x38FFFFFF),
                                Color(0x32193354),
                                Color(0x3E0F223D)
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
                .padding(18.dp)
                .testTag("language_selection_dialog")
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Header with icon and title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isLiquid) Color(0x3548CAE4)
                                    else MaterialTheme.colorScheme.primaryContainer
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Translate,
                                contentDescription = "Language",
                                tint = if (isLiquid) Color(0xFFFFD166) else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "भाषा चुनें",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = if (isLiquid || isDark) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = "16 Indian Languages",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isLiquid) Color(0xFFCBD5E1) else MaterialTheme.colorScheme.secondary
                                )
                            )
                        }
                    }

                    LiquidGlassIconButton(
                        icon = Icons.Default.Close,
                        contentDescription = "Close",
                        onClick = onDismiss,
                        size = 36.dp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Scrollable list of 16 Indian languages
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 420.dp),
                    contentPadding = PaddingValues(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(AppLanguage.values()) { lang ->
                        val isSelected = lang == currentLanguage

                        val itemBrush = when {
                            isSelected -> when {
                                isLiquid -> Brush.linearGradient(
                                    listOf(Color(0xE60077B6), Color(0xEE00B4D8), Color(0xEEFFD166))
                                )
                                isDark -> Brush.linearGradient(
                                    listOf(Color(0xFFB34A00), Color(0xFFFF9E3B))
                                )
                                else -> Brush.linearGradient(
                                    listOf(Color(0xFFC05809), Color(0xFFFF9E3B))
                                )
                            }
                            else -> when {
                                isLiquid -> Brush.verticalGradient(
                                    listOf(Color(0x24FFFFFF), Color(0x1418283E))
                                )
                                isDark -> Brush.verticalGradient(
                                    listOf(Color(0x353D2415), Color(0x2025150C))
                                )
                                else -> Brush.verticalGradient(
                                    listOf(Color(0xF0FFF8EE), Color(0xE0F5E6D3))
                                )
                            }
                        }

                        val borderBrush = when {
                            isSelected -> when {
                                isLiquid -> Brush.linearGradient(
                                    listOf(Color.White.copy(alpha = 0.85f), Color(0xFF48CAE4), Color.White.copy(alpha = 0.40f))
                                )
                                else -> Brush.verticalGradient(
                                    listOf(Color.White.copy(alpha = 0.70f), Color(0xFFFFD54F).copy(alpha = 0.55f), Color(0x30FFA000))
                                )
                            }
                            isLiquid -> Brush.linearGradient(
                                listOf(Color.White.copy(alpha = 0.35f), Color.Transparent)
                            )
                            else -> Brush.verticalGradient(
                                listOf(Color.White.copy(alpha = 0.25f), Color.White.copy(alpha = 0.08f))
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .liquidClickable(
                                    rippleColor = if (isLiquid) Color(0xFFFFD166) else SacredAmberRadiance,
                                    onClick = {
                                        onSelectLanguage(lang)
                                        onDismiss()
                                    }
                                )
                                .clip(RoundedCornerShape(14.dp))
                                .background(itemBrush)
                                .border(
                                    width = if (isSelected) 1.5.dp else 1.dp,
                                    brush = borderBrush,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .padding(horizontal = 14.dp, vertical = 10.dp)
                                .testTag("language_item_${lang.code}")
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Script abbreviation avatar
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isSelected) Color.White.copy(alpha = 0.25f)
                                                else if (isLiquid) Color(0x2848CAE4)
                                                else MaterialTheme.colorScheme.surfaceVariant
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = lang.code.uppercase(),
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.ExtraBold,
                                                fontSize = 11.sp,
                                                color = if (isSelected) Color.White
                                                else if (isLiquid) Color(0xFFFFD166)
                                                else MaterialTheme.colorScheme.primary
                                            )
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column {
                                        Text(
                                            text = lang.nativeName,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                                                fontSize = 16.sp,
                                                color = if (isSelected) Color.White
                                                else if (isLiquid || isDark) Color.White
                                                else Color(0xFF1E1B18)
                                            )
                                        )
                                        Text(
                                            text = "${lang.englishName} • ${lang.region}",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontSize = 11.sp,
                                                color = if (isSelected) Color.White.copy(alpha = 0.90f)
                                                else if (isLiquid) Color(0xFFCBD5E1)
                                                else MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        )
                                    }
                                }

                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(Color.White),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Selected",
                                            tint = if (isLiquid) Color(0xFF0077B6) else SaffronPrimary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
