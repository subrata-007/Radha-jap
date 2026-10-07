package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SoundType
import com.example.data.local.LifetimeMantraSummary
import com.example.data.local.MantraSadhana
import com.example.data.preferences.TapAreaMode
import com.example.localization.AppLanguage
import com.example.localization.LanguageProvider
import com.example.ui.components.BreathingJapButton
import com.example.ui.components.CustomDailyGoalDialog
import com.example.ui.components.GlassCard
import com.example.ui.components.LanguageSelectionDialog
import com.example.ui.components.LiquidGlassChip
import com.example.ui.components.LiquidGlassIconButton
import com.example.ui.components.MalaCelebrationDialog
import com.example.ui.components.MalaRingView
import com.example.ui.components.fluidAmbientBackdrop
import com.example.ui.components.liquidClickable
import com.example.ui.theme.LiquidGlassBackground
import com.example.ui.theme.SacredAmberRadiance
import com.example.ui.theme.SacredGoldGlow
import com.example.ui.theme.TulsiTertiary
import com.example.viewmodel.JapUiState

val PRESET_MANTRAS = listOf(
    "श्री राधा",
    "राधे राधे",
    "हरे कृष्ण हरे कृष्ण कृष्ण कृष्ण हरे हरे \nहरे राम हरे राम राम राम हरे हरे",
    "श्री राधा कृष्ण",
    "राधा गोविन्द",
    "ॐ नमो भगवते वासुदेवाय"
)

val DEVOTIONAL_QUOTES = listOf(
    "« जो जन जपहिं सदा राधा नाम, ताहि न व्यापहिं कलिकाल के काम। »",
    "« राधा नाम परम सुखदाई, भज मन मेरे सदा सुखदाई। »",
    "« कोटि कल्प के पाप कटे, मुख निसरै राधा नाम। »",
    "« सकल मनोरथ पूर्ण हों, जपिये श्री राधा नाम। »",
    "« राधा नाम रस पीवै सोई, जेहि पर कृपा किशोरी की होई। »"
)

@Composable
fun CounterScreen(
    uiState: JapUiState,
    lifetimeTotalBeads: Int,
    lifetimeTotalMalas: Int,
    todayMantraList: List<MantraSadhana> = emptyList(),
    lifetimeMantraBreakdown: List<LifetimeMantraSummary> = emptyList(),
    onIncrement: () -> Unit,
    onUndo: () -> Unit,
    onResetCurrentMala: () -> Unit,
    onSelectMantra: (String) -> Unit,
    onToggleDrone: () -> Unit,
    onCycleSound: () -> Unit,
    onToggleTheme: () -> Unit,
    onUpdateSankalpaTarget: (Int) -> Unit,
    onOpenDhyanMode: () -> Unit,
    onDismissCelebration: () -> Unit,
    onSelectLanguage: (AppLanguage) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val isFullScreenTap = uiState.tapAreaMode == TapAreaMode.FULL_SCREEN
    val interactionSource = remember { MutableInteractionSource() }
    val haptic = LocalHapticFeedback.current
    var showGoalDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var quoteIndex by remember { mutableIntStateOf(0) }

    val strings = remember(uiState.appLanguage) {
        LanguageProvider.getStrings(uiState.appLanguage)
    }

    val availableMantras = remember(strings.presetMantras, todayMantraList, lifetimeMantraBreakdown, uiState.currentMantra) {
        val list = strings.presetMantras.toMutableList()
        todayMantraList.forEach { if (!list.contains(it.mantraName)) list.add(it.mantraName) }
        lifetimeMantraBreakdown.forEach { if (!list.contains(it.mantraName)) list.add(it.mantraName) }
        if (!list.contains(uiState.currentMantra)) list.add(uiState.currentMantra)
        list
    }

    val isLiquid = MaterialTheme.colorScheme.background == LiquidGlassBackground

    if (showLanguageDialog) {
        LanguageSelectionDialog(
            currentLanguage = uiState.appLanguage,
            onSelectLanguage = onSelectLanguage,
            onDismiss = { showLanguageDialog = false }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .fluidAmbientBackdrop(uiState.themeMode)
            .then(
                if (isFullScreenTap) {
                    Modifier.liquidClickable(
                        rippleColor = if (isLiquid) Color(0xFF48CAE4) else SacredGoldGlow,
                        onClick = onIncrement
                    )
                } else Modifier
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Mantra Selector Chips with Liquid Effects & Separate counts
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(availableMantras) { mantra ->
                    val isSelected = uiState.currentMantra == mantra
                    val isMahamantra = mantra.contains("हरे कृष्ण") || mantra.contains("হরে কৃষ্ণ") || mantra.contains("హరే కృష్ణ") || mantra.contains("Hare Krishna") || mantra.contains("ஹரே கிருஷ்ண") || mantra.contains("ഹരേ കൃഷ്ണ") || mantra.contains("ਹਰੇ ਕ੍ਰਿਸ਼ਨ") || mantra.contains("હરે કૃષ્ણ")
                    val displayShort = if (isMahamantra) {
                        when (uiState.appLanguage) {
                            com.example.localization.AppLanguage.ENGLISH -> "Mahamantra"
                            com.example.localization.AppLanguage.BENGALI -> "মহামন্ত্র"
                            com.example.localization.AppLanguage.GUJARATI -> "મહામંત્ર"
                            com.example.localization.AppLanguage.TAMIL -> "மஹாமந்திரம்"
                            com.example.localization.AppLanguage.TELUGU -> "మహామంత్రం"
                            com.example.localization.AppLanguage.KANNADA -> "ಮಹಾಮಂತ್ರ"
                            com.example.localization.AppLanguage.MALAYALAM -> "മഹാമന്ത്രം"
                            com.example.localization.AppLanguage.ODIA -> "ମହାମନ୍ତ୍ର"
                            com.example.localization.AppLanguage.PUNJABI -> "ਮਹਾਮੰਤਰ"
                            com.example.localization.AppLanguage.ASSAMESE -> "মহামন্ত্ৰ"
                            else -> "महामंत्र"
                        }
                    } else mantra.take(12)
                    val todayCount = todayMantraList.find { it.mantraName == mantra }
                    val malasChanted = todayCount?.totalMalas ?: 0
                    val beadsChanted = todayCount?.totalBeads ?: 0

                    LiquidGlassChip(
                        selected = isSelected,
                        onClick = { onSelectMantra(mantra) },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = displayShort,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                    )
                                )
                                if (malasChanted > 0) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "(${malasChanted} ${strings.malas.take(2)})",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f) else MaterialTheme.colorScheme.primary
                                        )
                                    )
                                } else if (beadsChanted > 0) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "(${beadsChanted} ${strings.bead.take(2)})",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f) else MaterialTheme.colorScheme.secondary
                                        )
                                    )
                                }
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Central 108 Beads Mala Canvas & Breathing Touch Button
            MalaRingView(
                currentBead = uiState.currentBead,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                BreathingJapButton(
                    currentBead = uiState.currentBead,
                    mantraText = uiState.currentMantra,
                    tapPrompt = strings.tapToChant,
                    onTap = onIncrement
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Prominent Mala Status Capsule (Guarantees Mala text is 100% visible & readable)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (isLiquid) Color(0x2848CAE4)
                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    )
                    .border(
                        width = 1.dp,
                        brush = if (isLiquid) {
                            Brush.linearGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.85f),
                                    Color(0x9948CAE4),
                                    Color(0x66FFD166),
                                    Color.White.copy(alpha = 0.25f)
                                )
                            )
                        } else {
                            Brush.verticalGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.60f),
                                    Color.Transparent
                                )
                            )
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 14.dp, vertical = 7.dp)
                    .testTag("mala_status_capsule")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${strings.thisMantraMala}: ",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    AnimatedContent(
                        targetState = uiState.currentMantraTodayMalas + 1,
                        transitionSpec = {
                            (slideInVertically(spring(dampingRatio = 0.72f, stiffness = 380f)) { -it } + fadeIn(tween(160))) togetherWith
                            (slideOutVertically(spring(dampingRatio = 0.72f, stiffness = 380f)) { it } + fadeOut(tween(130)))
                        },
                        label = "currentMalaCountAnim"
                    ) { count ->
                        Text(
                            text = "$count",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    Text(
                        text = "  •  ",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                    Text(
                        text = "${strings.bead}: ",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    AnimatedContent(
                        targetState = uiState.currentBead + 1,
                        transitionSpec = {
                            (slideInVertically(spring(dampingRatio = 0.72f, stiffness = 380f)) { -it } + fadeIn(tween(160))) togetherWith
                            (slideOutVertically(spring(dampingRatio = 0.72f, stiffness = 380f)) { it } + fadeOut(tween(130)))
                        },
                        label = "currentBeadCountAnim"
                    ) { bead ->
                        Text(
                            text = "$bead / 108",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                    Text(
                        text = "  •  ",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                    Text(
                        text = "${strings.totalToday}: ",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                    AnimatedContent(
                        targetState = uiState.todayMalas,
                        transitionSpec = {
                            (slideInVertically(spring(dampingRatio = 0.72f, stiffness = 380f)) { -it } + fadeIn(tween(160))) togetherWith
                            (slideOutVertically(spring(dampingRatio = 0.72f, stiffness = 380f)) { it } + fadeOut(tween(130)))
                        },
                        label = "todayMalasCountAnim"
                    ) { malas ->
                        Text(
                            text = "$malas ${strings.malas}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Control Buttons Row - Clean, floating liquid crystal buttons without black bar behind them
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Undo Button with Liquid Glass feedback
                LiquidGlassIconButton(
                    icon = Icons.Default.Undo,
                    contentDescription = "Undo",
                    onClick = onUndo,
                    enabled = uiState.canUndo,
                    size = 46.dp,
                    tint = if (uiState.canUndo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    modifier = Modifier.testTag("undo_button")
                )

                // Sound Mode Toggle with Liquid Glass feedback
                val soundIcon = when (uiState.soundType) {
                    SoundType.SILENT -> Icons.Default.VolumeMute
                    SoundType.BELL -> Icons.Default.Notifications
                    SoundType.FLUTE -> Icons.Default.MusicNote
                    SoundType.SHANKH -> Icons.AutoMirrored.Filled.VolumeUp
                    SoundType.TULSI_BEAD -> Icons.Default.Spa
                }
                LiquidGlassIconButton(
                    icon = soundIcon,
                    contentDescription = "Sound Mode",
                    onClick = onCycleSound,
                    size = 46.dp,
                    modifier = Modifier.testTag("sound_toggle_button")
                )

                // Ambient Drone Toggle with glowing Liquid feedback
                LiquidGlassIconButton(
                    icon = Icons.Default.GraphicEq,
                    contentDescription = "Tanpura Drone",
                    onClick = onToggleDrone,
                    activeGlow = uiState.isDronePlaying,
                    size = 46.dp,
                    tint = if (uiState.isDronePlaying) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.testTag("drone_toggle_button")
                )

                // Reset Current Mala with Liquid Glass feedback
                LiquidGlassIconButton(
                    icon = Icons.Default.Refresh,
                    contentDescription = "Reset Current Mala",
                    onClick = onResetCurrentMala,
                    size = 46.dp,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.testTag("reset_mala_button")
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Today's Sadhana & Sankalpa Progress - Fluid Material Glass Card
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("sadhana_summary_card"),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Sankalpa Header with Quick Custom Goal Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = strings.dailyGoalTitle,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            // Interactive Pen icon to set Custom Daily Goal Malas
                            LiquidGlassIconButton(
                                icon = Icons.Default.Edit,
                                contentDescription = strings.customGoal,
                                onClick = { showGoalDialog = true },
                                size = 32.dp,
                                modifier = Modifier.testTag("edit_custom_goal_button")
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (uiState.isSankalpaDone) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .liquidClickable(onClick = { showGoalDialog = true })
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(TulsiTertiary.copy(alpha = 0.20f))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Completed",
                                        tint = TulsiTertiary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = strings.acceptCelebration,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = TulsiTertiary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .liquidClickable(onClick = { showGoalDialog = true })
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Text(
                                        text = "${uiState.todayMalas} / ${uiState.sankalpaTargetMalas} ${strings.malas}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val animatedLinearProgress by animateFloatAsState(
                        targetValue = (uiState.todayMalas.toFloat() / uiState.sankalpaTargetMalas.toFloat()).coerceIn(0f, 1f),
                        animationSpec = spring(dampingRatio = 0.75f, stiffness = 300f),
                        label = "sankalpaProgressAnimation"
                    )

                    LinearProgressIndicator(
                        progress = { animatedLinearProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = if (uiState.isSankalpaDone) TulsiTertiary else MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
                        strokeCap = StrokeCap.Round
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // 3 Column Quick Stats with Liquid Touch feedback
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickStatItem(
                            title = strings.thisMantraMala,
                            value = "${uiState.currentMantraTodayMalas} ${strings.malas.take(2)} (${uiState.currentMantraTodayBeads % 108}/108)"
                        )
                        QuickStatItem(
                            title = strings.totalToday,
                            value = "${uiState.todayMalas} ${strings.malas}"
                        )
                        QuickStatItem(
                            title = strings.lifetimeTotal,
                            value = "$lifetimeTotalMalas ${strings.malas}"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Dedicated Mantra-Wise Records Card (Records how many times each mantra was chanted separately)
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "📿",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Column {
                                Text(
                                    text = strings.mantraWiseRecords,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${strings.totalToday} • ${strings.lifetimeTotal}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "${todayMantraList.size} ${strings.selectMantra}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (todayMantraList.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.4f))
                                .padding(vertical = 14.dp, horizontal = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${strings.todaySadhanaSummary} — ${strings.tapToChant}!",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            todayMantraList.forEach { record ->
                                val isActive = record.mantraName == uiState.currentMantra
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (isActive) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                            else MaterialTheme.colorScheme.surface.copy(alpha = 0.35f)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (isActive) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else Color.Transparent,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .liquidClickable(onClick = { onSelectMantra(record.mantraName) })
                                        .padding(horizontal = 12.dp, vertical = 9.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = record.mantraName.take(22),
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.SemiBold,
                                                    color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                                )
                                            )
                                            if (isActive) {
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = "(${strings.thisMantraMala})",
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        color = MaterialTheme.colorScheme.primary,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "${strings.lifetimeBeads}: ${record.totalBeads} • ${strings.bead}: ${record.totalBeads % 108}/108",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            text = "${record.totalMalas} ${strings.malas}",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.ExtraBold,
                                                color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Peaceful Daily Quote Banner with Liquid click to cycle
            val quotes = remember(strings.devotionalQuotes) {
                if (strings.devotionalQuotes.isNotEmpty()) strings.devotionalQuotes else DEVOTIONAL_QUOTES
            }
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    quoteIndex = (quoteIndex + 1) % quotes.size
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🪷",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Text(
                        text = quotes[quoteIndex % quotes.size],
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }
    }

    // Celebration pop-up on 108 completion
    if (uiState.showCelebrationDialog) {
        MalaCelebrationDialog(
            malaCount = uiState.celebrationMalaCount,
            mantraName = uiState.celebrationMantraName,
            totalTodayMalas = uiState.todayMalas,
            language = uiState.appLanguage,
            onDismiss = onDismissCelebration
        )
    }

    // Custom Daily Goal Malas Dialog
    if (showGoalDialog) {
        CustomDailyGoalDialog(
            currentTarget = uiState.sankalpaTargetMalas,
            language = uiState.appLanguage,
            onConfirm = { newTarget ->
                onUpdateSankalpaTarget(newTarget)
            },
            onDismiss = { showGoalDialog = false }
        )
    }
}

@Composable
fun QuickStatItem(
    title: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
        )
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}
