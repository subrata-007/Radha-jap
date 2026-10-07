package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SoundType
import com.example.data.local.LifetimeMantraSummary
import com.example.data.local.MantraSadhana
import com.example.data.preferences.HapticStrength
import com.example.data.preferences.TapAreaMode
import com.example.localization.AppLanguage
import com.example.localization.LanguageProvider
import com.example.ui.components.CustomDailyGoalDialog
import com.example.ui.components.GlassCard
import com.example.ui.components.LanguageSelectionDialog
import com.example.ui.components.LiquidGlassButton
import com.example.ui.components.LiquidGlassChip
import com.example.ui.components.LiquidGlassIconButton
import com.example.ui.components.fluidAmbientBackdrop
import com.example.ui.components.liquidClickable
import com.example.viewmodel.JapUiState

val SANKALPA_PRESETS = listOf(1, 4, 11, 16, 21, 32, 64, 108)

@Composable
fun SankalpaSettingsScreen(
    uiState: JapUiState,
    todayMantraList: List<MantraSadhana> = emptyList(),
    lifetimeMantraBreakdown: List<LifetimeMantraSummary> = emptyList(),
    onSetSankalpa: (Int) -> Unit,
    onSetMantra: (String) -> Unit,
    onSetSoundType: (SoundType) -> Unit,
    onSetHapticStrength: (HapticStrength) -> Unit,
    onSetTapAreaMode: (TapAreaMode) -> Unit,
    onSetVolumeKeysEnabled: (Boolean) -> Unit,
    onSetThemeMode: (String) -> Unit,
    onSetAppLanguage: (AppLanguage) -> Unit = {},
    onSetHideStatusBar: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showCustomMantraDialog by remember { mutableStateOf(false) }
    var showCustomGoalDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var customMantraInput by remember { mutableStateOf("") }

    val strings = remember(uiState.appLanguage) {
        LanguageProvider.getStrings(uiState.appLanguage)
    }

    val allMantras = remember(strings.presetMantras, todayMantraList, lifetimeMantraBreakdown, uiState.currentMantra) {
        val list = strings.presetMantras.toMutableList()
        todayMantraList.forEach { if (!list.contains(it.mantraName)) list.add(it.mantraName) }
        lifetimeMantraBreakdown.forEach { if (!list.contains(it.mantraName)) list.add(it.mantraName) }
        if (!list.contains(uiState.currentMantra)) list.add(uiState.currentMantra)
        list
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .fluidAmbientBackdrop(uiState.themeMode)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("sankalpa_settings_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Text(
                    text = strings.settingsTitle,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = strings.settingsSubtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Section 0: 16 Indian Languages Selector
            item {
                SettingsCard(title = strings.languageTitle) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f, fill = false)) {
                                Text(
                                    text = "${uiState.appLanguage.nativeName} (${uiState.appLanguage.englishName})",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = uiState.appLanguage.region,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            // Sleek, compact and elegant Language Button
                            LiquidGlassIconButton(
                                icon = Icons.Default.Translate,
                                contentDescription = strings.languageTitle,
                                onClick = { showLanguageDialog = true },
                                size = 38.dp,
                                modifier = Modifier.testTag("open_all_languages_btn")
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(AppLanguage.values()) { lang ->
                                val isSelected = lang == uiState.appLanguage
                                LiquidGlassChip(
                                    selected = isSelected,
                                    onClick = { onSetAppLanguage(lang) },
                                    label = {
                                        Text(
                                            text = lang.nativeName,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Section 1: Daily Sankalpa Target (Custom Goal)
            item {
                SettingsCard(title = strings.dailyGoalTitle) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${uiState.sankalpaTargetMalas} ${strings.malas} (${uiState.sankalpaTargetMalas * 108} ${strings.beads})",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )

                            LiquidGlassButton(
                                onClick = { showCustomGoalDialog = true },
                                primary = false,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.testTag("open_custom_goal_dialog_btn")
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(strings.customGoal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(SANKALPA_PRESETS) { target ->
                                val isSelected = uiState.sankalpaTargetMalas == target
                                LiquidGlassChip(
                                    selected = isSelected,
                                    onClick = { onSetSankalpa(target) },
                                    label = {
                                        Text(
                                            text = "$target ${strings.malas}",
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                        )
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Stepper for custom adjustments with Liquid Glass Icon Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "± 1 (${strings.malas}):",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                LiquidGlassIconButton(
                                    icon = Icons.Default.Remove,
                                    contentDescription = "Decrease",
                                    onClick = { onSetSankalpa((uiState.sankalpaTargetMalas - 1).coerceAtLeast(1)) },
                                    size = 38.dp
                                )
                                Text(
                                    text = "${uiState.sankalpaTargetMalas}",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )
                                LiquidGlassIconButton(
                                    icon = Icons.Default.Add,
                                    contentDescription = "Increase",
                                    onClick = { onSetSankalpa(uiState.sankalpaTargetMalas + 1) },
                                    size = 38.dp
                                )
                            }
                        }
                    }
                }
            }

            // Section 2: Mantra Selection & Separate Records
            item {
                SettingsCard(title = strings.selectMantra) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        allMantras.forEach { mantra ->
                            val isSelected = uiState.currentMantra == mantra
                            val todayRec = todayMantraList.find { it.mantraName == mantra }
                            val lifetimeRec = lifetimeMantraBreakdown.find { it.mantraName == mantra }

                            LiquidGlassChip(
                                selected = isSelected,
                                onClick = { onSetMantra(mantra) },
                                modifier = Modifier.fillMaxWidth(),
                                icon = if (isSelected) {
                                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                } else null,
                                label = {
                                    Column(modifier = Modifier.padding(vertical = 2.dp)) {
                                        Text(
                                            text = mantra,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                            )
                                        )
                                        if (todayRec != null || lifetimeRec != null) {
                                            val todayMalas = todayRec?.totalMalas ?: 0
                                            val todayBeads = todayRec?.totalBeads ?: 0
                                            val lifetimeMalas = lifetimeRec?.lifetimeMalas ?: 0
                                            Text(
                                                text = "${strings.totalToday}: $todayMalas ${strings.malas} ($todayBeads ${strings.beads}) • ${strings.lifetimeTotal}: $lifetimeMalas ${strings.malas}",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontSize = 11.sp,
                                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                                                    else MaterialTheme.colorScheme.secondary
                                                )
                                            )
                                        }
                                    }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        LiquidGlassButton(
                            onClick = {
                                customMantraInput = uiState.currentMantra
                                showCustomMantraDialog = true
                            },
                            primary = false,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(strings.customMantra, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Section 3: Audio Feedback
            item {
                SettingsCard(title = strings.soundFeedback) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        SoundType.values().forEach { sound ->
                            val isSelected = uiState.soundType == sound
                            LiquidGlassChip(
                                selected = isSelected,
                                onClick = { onSetSoundType(sound) },
                                modifier = Modifier.fillMaxWidth(),
                                icon = if (isSelected) {
                                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                } else null,
                                label = {
                                    Text(
                                        text = LanguageProvider.getSoundTitle(uiState.appLanguage, sound),
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            )
                        }
                    }
                }
            }

            // Section 4: Haptic Vibration
            item {
                SettingsCard(title = strings.vibrationFeedback) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        HapticStrength.values().forEach { strength ->
                            val isSelected = uiState.hapticStrength == strength
                            LiquidGlassChip(
                                selected = isSelected,
                                onClick = { onSetHapticStrength(strength) },
                                modifier = Modifier.weight(1f),
                                label = {
                                    Text(
                                        text = LanguageProvider.getHapticTitle(uiState.appLanguage, strength),
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            )
                        }
                    }
                }
            }

            // Section 5: Interaction & Hardware
            item {
                SettingsCard(title = strings.touchHardware) {
                    Column {
                        // Tap Anywhere Mode Switch
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .liquidClickable {
                                    onSetTapAreaMode(
                                        if (uiState.tapAreaMode == TapAreaMode.FULL_SCREEN) TapAreaMode.BUTTON_ONLY
                                        else TapAreaMode.FULL_SCREEN
                                    )
                                }
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = strings.tapAnywhere,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                                )
                                Text(
                                    text = strings.tapAnywhereDesc,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = uiState.tapAreaMode == TapAreaMode.FULL_SCREEN,
                                onCheckedChange = { checked ->
                                    onSetTapAreaMode(if (checked) TapAreaMode.FULL_SCREEN else TapAreaMode.BUTTON_ONLY)
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Volume Keys Counter Switch
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .liquidClickable {
                                    onSetVolumeKeysEnabled(!uiState.volumeKeysEnabled)
                                }
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = strings.volumeKeys,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                                )
                                Text(
                                    text = strings.volumeKeysDesc,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = uiState.volumeKeysEnabled,
                                onCheckedChange = onSetVolumeKeysEnabled
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Fullscreen / Hide Status Bar Immersive Switch
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .liquidClickable {
                                    onSetHideStatusBar(!uiState.hideStatusBar)
                                }
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = LanguageProvider.getImmersiveModeTitle(uiState.appLanguage),
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                                )
                                Text(
                                    text = LanguageProvider.getImmersiveModeDesc(uiState.appLanguage),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = uiState.hideStatusBar,
                                onCheckedChange = onSetHideStatusBar
                            )
                        }
                    }
                }
            }

            // Section 6: App Theme
            item {
                SettingsCard(title = strings.themeTitle) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val themes = listOf(
                            "liquid_glass" to strings.themeGlass,
                            "light" to strings.themeLight,
                            "dark" to strings.themeDark,
                            "system" to strings.themeAuto
                        )
                        items(themes) { (mode, label) ->
                            val isSelected = uiState.themeMode == mode
                            LiquidGlassChip(
                                selected = isSelected,
                                onClick = { onSetThemeMode(mode) },
                                label = {
                                    Text(
                                        text = label,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            )
                        }
                    }
                }
            }

            // Section 7: Devotional Dedication Card
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🪷", fontSize = 28.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = strings.devotionalFooter,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = strings.blessing,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }

    if (showCustomGoalDialog) {
        CustomDailyGoalDialog(
            currentTarget = uiState.sankalpaTargetMalas,
            language = uiState.appLanguage,
            onConfirm = { newTarget ->
                onSetSankalpa(newTarget)
            },
            onDismiss = { showCustomGoalDialog = false }
        )
    }

    if (showCustomMantraDialog) {
        AlertDialog(
            onDismissRequest = { showCustomMantraDialog = false },
            title = { Text(strings.customMantraDialogTitle) },
            text = {
                OutlinedTextField(
                    value = customMantraInput,
                    onValueChange = { customMantraInput = it },
                    label = { Text(strings.enterMantra) },
                    singleLine = false,
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (customMantraInput.isNotBlank()) {
                            onSetMantra(customMantraInput.trim())
                        }
                        showCustomMantraDialog = false
                    }
                ) {
                    Text(strings.submit)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCustomMantraDialog = false }) {
                    Text(strings.cancel)
                }
            }
        )
    }

    if (showLanguageDialog) {
        LanguageSelectionDialog(
            currentLanguage = uiState.appLanguage,
            onSelectLanguage = {
                onSetAppLanguage(it)
                showLanguageDialog = false
            },
            onDismiss = { showLanguageDialog = false }
        )
    }
}

@Composable
fun SettingsCard(
    title: String,
    content: @Composable () -> Unit
) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}
