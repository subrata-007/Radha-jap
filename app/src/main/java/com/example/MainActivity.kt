package com.example

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.audio.SoundType
import com.example.localization.AppLanguage
import com.example.localization.LanguageProvider
import com.example.ui.components.ExitConfirmDialog
import com.example.ui.components.LanguageSelectionDialog
import com.example.ui.components.LiquidGlassIconButton
import com.example.ui.components.liquidClickable
import com.example.ui.screens.CounterScreen
import com.example.ui.screens.DhyanScreen
import com.example.ui.screens.SadhanaHistoryScreen
import com.example.ui.screens.SankalpaSettingsScreen
import com.example.ui.theme.LiquidGlassBackground
import com.example.ui.theme.RadhaJapTheme
import com.example.ui.theme.SacredAmberRadiance
import com.example.ui.theme.SacredGoldGlow
import com.example.viewmodel.JapViewModel

enum class ScreenTab {
    COUNTER,
    HISTORY,
    SETTINGS
}

class MainActivity : ComponentActivity() {

    private val viewModel: JapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Keep screen awake during peaceful meditation
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)

        // Request 120 FPS high refresh rate display mode if supported by device
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val display = display
                val supportedModes = display?.supportedModes
                val maxRefreshMode = supportedModes?.maxByOrNull { it.refreshRate }
                if (maxRefreshMode != null && maxRefreshMode.refreshRate >= 90f) {
                    val lp = window.attributes
                    lp.preferredDisplayModeId = maxRefreshMode.modeId
                    window.attributes = lp
                }
            } catch (_: Exception) {}
        }

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val recentSadhana by viewModel.recentSadhana.collectAsStateWithLifecycle()
            val todayMantraList by viewModel.todayMantraBreakdown.collectAsStateWithLifecycle()
            val lifetimeMantraBreakdown by viewModel.lifetimeMantraBreakdown.collectAsStateWithLifecycle()
            val lifetimeBeads by viewModel.lifetimeBeads.collectAsStateWithLifecycle()
            val lifetimeMalas by viewModel.lifetimeMalas.collectAsStateWithLifecycle()

            val isSystemDark = isSystemInDarkTheme()

            val strings = remember(uiState.appLanguage) {
                LanguageProvider.getStrings(uiState.appLanguage)
            }

            val insetsController = remember(window) {
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
            LaunchedEffect(uiState.hideStatusBar) {
                if (uiState.hideStatusBar) {
                    insetsController.hide(WindowInsetsCompat.Type.statusBars())
                } else {
                    insetsController.show(WindowInsetsCompat.Type.statusBars())
                }
            }

            var currentTab by remember { mutableStateOf(ScreenTab.COUNTER) }
            var isDhyanModeOpen by remember { mutableStateOf(false) }
            var showExitConfirmDialog by remember { mutableStateOf(false) }
            var showLanguageDialog by remember { mutableStateOf(false) }

            // Intercept System Back navigation:
            // 1. If in Dhyan (Meditation) mode, gracefully return to Counter
            BackHandler(enabled = isDhyanModeOpen) {
                isDhyanModeOpen = false
            }

            // 2. If on History or Settings sub-screens, navigate back to the main Counter tab
            BackHandler(enabled = !isDhyanModeOpen && currentTab != ScreenTab.COUNTER) {
                currentTab = ScreenTab.COUNTER
            }

            // 3. If on main Counter tab, prompt devotee with glassmorphic exit confirmation dialog
            BackHandler(enabled = !isDhyanModeOpen && currentTab == ScreenTab.COUNTER && !showExitConfirmDialog) {
                showExitConfirmDialog = true
            }

            RadhaJapTheme(themeMode = uiState.themeMode, darkTheme = isSystemDark) {
                // Exit Confirmation Glassmorphic Dialog
                if (showExitConfirmDialog) {
                    ExitConfirmDialog(
                        currentMantra = uiState.currentMantra,
                        currentBead = uiState.currentBead + 1,
                        todayMalas = uiState.currentMantraTodayMalas,
                        language = uiState.appLanguage,
                        onContinue = { showExitConfirmDialog = false },
                        onConfirmExit = {
                            showExitConfirmDialog = false
                            finish()
                        }
                    )
                }

                // 16 Indian Languages Selection Dialog
                if (showLanguageDialog) {
                    LanguageSelectionDialog(
                        currentLanguage = uiState.appLanguage,
                        onSelectLanguage = {
                            viewModel.setAppLanguage(it)
                            showLanguageDialog = false
                        },
                        onDismiss = { showLanguageDialog = false }
                    )
                }

                if (isDhyanModeOpen) {
                    DhyanScreen(
                        uiState = uiState,
                        onIncrement = { viewModel.incrementBead() },
                        onToggleDrone = { viewModel.toggleDrone() },
                        onExit = { isDhyanModeOpen = false },
                        onDismissCelebration = { viewModel.dismissCelebration() }
                    )
                } else {
                    val isLiquid = uiState.themeMode == "liquid_glass"
                    val isDark = uiState.themeMode == "dark"

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        contentWindowInsets = WindowInsets(0, 0, 0, 0),
                        topBar = {
                            // Permanent Top Bar matching bottom navigation in glassmorphism and permanence
                            val statusBarInsets = if (uiState.hideStatusBar) WindowInsets(0, 0, 0, 0) else WindowInsets.statusBars
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .windowInsetsPadding(statusBarInsets)
                                    .background(
                                        when {
                                            isLiquid -> Color(0xCC0E1F36)
                                            isDark -> Color(0xCC26150C)
                                            else -> Color(0xE6FFFDF8)
                                        }
                                    )
                                    .border(
                                        width = 1.dp,
                                        brush = Brush.verticalGradient(
                                            listOf(
                                                Color.White.copy(alpha = if (isLiquid) 0.15f else 0.05f),
                                                Color.White.copy(alpha = if (isLiquid) 0.80f else 0.40f)
                                            )
                                        ),
                                        shape = androidx.compose.ui.graphics.RectangleShape
                                    )
                                    .testTag("permanent_top_bar")
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 14.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = strings.appName,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.ExtraBold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        )
                                        Text(
                                            text = "108 ${strings.beads} • ${uiState.appLanguage.nativeName}",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        )
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        // Jewel Streak Badge
                                        Box(
                                            modifier = Modifier
                                                .padding(end = 6.dp)
                                                .clip(RoundedCornerShape(20.dp))
                                                .background(
                                                    when {
                                                        isLiquid -> Brush.horizontalGradient(listOf(Color(0x3500B4D8), Color(0x200077B6)))
                                                        isDark -> Brush.horizontalGradient(listOf(Color(0x45FF9E3B), Color(0x25FFA000), Color(0x183D2415)))
                                                        else -> Brush.horizontalGradient(listOf(Color(0x25FF9E3B), Color(0x15FFA000)))
                                                    }
                                                )
                                                .border(
                                                    width = 1.dp,
                                                    brush = Brush.horizontalGradient(
                                                        listOf(Color(0x99FFD54F), Color(0x40FFA000))
                                                    ),
                                                    shape = RoundedCornerShape(20.dp)
                                                )
                                                .padding(horizontal = 9.dp, vertical = 5.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.Whatshot,
                                                    contentDescription = "Streak",
                                                    tint = SacredAmberRadiance,
                                                    modifier = Modifier.size(15.dp)
                                                )
                                                Spacer(modifier = Modifier.width(3.dp))
                                                Text(
                                                    text = "${uiState.streakDays} ${strings.days}",
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontWeight = FontWeight.ExtraBold,
                                                        fontSize = 11.5.sp
                                                    ),
                                                    color = if (isDark || isLiquid) SacredGoldGlow else MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        }

                                        // Sleek Compact Language Selector Button (36.dp)
                                        LiquidGlassIconButton(
                                            icon = Icons.Default.Translate,
                                            contentDescription = strings.languageTitle,
                                            onClick = { showLanguageDialog = true },
                                            size = 36.dp,
                                            modifier = Modifier.testTag("permanent_language_button")
                                        )

                                        Spacer(modifier = Modifier.width(6.dp))

                                        // Theme Toggle with Glassmorphism Crystal Button
                                        val (themeIcon, themeDesc) = when (uiState.themeMode) {
                                            "liquid_glass" -> Icons.Default.WaterDrop to strings.themeGlass
                                            "light" -> Icons.Default.LightMode to strings.themeLight
                                            "dark" -> Icons.Default.DarkMode to strings.themeDark
                                            else -> Icons.Default.WaterDrop to strings.themeGlass
                                        }
                                        LiquidGlassIconButton(
                                            icon = themeIcon,
                                            contentDescription = themeDesc,
                                            onClick = {
                                                val nextTheme = when (uiState.themeMode) {
                                                    "light" -> "dark"
                                                    "dark" -> "liquid_glass"
                                                    "liquid_glass" -> "light"
                                                    else -> "liquid_glass"
                                                }
                                                viewModel.setThemeMode(nextTheme)
                                            },
                                            size = 36.dp,
                                            modifier = Modifier.testTag("permanent_theme_button")
                                        )

                                        Spacer(modifier = Modifier.width(6.dp))

                                        // Dhyan Mode button with Liquid Glass Crystal Button
                                        LiquidGlassIconButton(
                                            icon = Icons.Default.SelfImprovement,
                                            contentDescription = strings.dhyanMode,
                                            onClick = { isDhyanModeOpen = true },
                                            size = 36.dp,
                                            modifier = Modifier.testTag("permanent_dhyan_button")
                                        )
                                    }
                                }
                            }
                        },
                        bottomBar = {
                            // Ultra-Glossy Liquid Glass Bottom Navigation Bar
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .windowInsetsPadding(WindowInsets.navigationBars)
                                    .background(
                                        when {
                                            isLiquid -> Color(0xCC0E1F36)
                                            isDark -> Color(0xCC26150C)
                                            else -> Color(0xE6FFFDF8)
                                        }
                                    )
                                    .border(
                                        width = 1.dp,
                                        brush = Brush.verticalGradient(
                                            listOf(
                                                Color.White.copy(alpha = if (isLiquid) 0.80f else 0.40f),
                                                Color.White.copy(alpha = if (isLiquid) 0.15f else 0.05f)
                                            )
                                        ),
                                        shape = androidx.compose.ui.graphics.RectangleShape
                                    )
                                    .testTag("bottom_nav_bar")
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    LiquidTabItem(
                                        title = strings.tabJap,
                                        icon = Icons.Default.TouchApp,
                                        selected = currentTab == ScreenTab.COUNTER,
                                        isLiquid = isLiquid,
                                        testTag = "tab_counter",
                                        onClick = { currentTab = ScreenTab.COUNTER }
                                    )

                                    LiquidTabItem(
                                        title = strings.tabSadhana,
                                        icon = Icons.Default.Assessment,
                                        selected = currentTab == ScreenTab.HISTORY,
                                        isLiquid = isLiquid,
                                        testTag = "tab_history",
                                        onClick = { currentTab = ScreenTab.HISTORY }
                                    )

                                    LiquidTabItem(
                                        title = strings.tabGoal,
                                        icon = Icons.Default.Flag,
                                        selected = currentTab == ScreenTab.SETTINGS,
                                        isLiquid = isLiquid,
                                        testTag = "tab_settings",
                                        onClick = { currentTab = ScreenTab.SETTINGS }
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        val screenModifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)

                        AnimatedContent(
                            targetState = currentTab,
                            transitionSpec = {
                                (fadeIn(animationSpec = tween(260, easing = FastOutSlowInEasing)) +
                                scaleIn(initialScale = 0.97f, animationSpec = tween(260, easing = FastOutSlowInEasing))) togetherWith
                                fadeOut(animationSpec = tween(180, easing = FastOutSlowInEasing))
                            },
                            label = "tabScreenTransition"
                        ) { targetTab ->
                            when (targetTab) {
                                ScreenTab.COUNTER -> {
                                    CounterScreen(
                                        uiState = uiState,
                                        lifetimeTotalBeads = lifetimeBeads,
                                        lifetimeTotalMalas = lifetimeMalas,
                                        todayMantraList = todayMantraList,
                                        lifetimeMantraBreakdown = lifetimeMantraBreakdown,
                                        onIncrement = { viewModel.incrementBead() },
                                        onUndo = { viewModel.undoLastBead() },
                                        onResetCurrentMala = { viewModel.resetCurrentMala() },
                                        onSelectMantra = { viewModel.setMantra(it) },
                                        onToggleDrone = { viewModel.toggleDrone() },
                                        onCycleSound = {
                                            val nextSound = when (uiState.soundType) {
                                                SoundType.BELL -> SoundType.FLUTE
                                                SoundType.FLUTE -> SoundType.SHANKH
                                                SoundType.SHANKH -> SoundType.TULSI_BEAD
                                                SoundType.TULSI_BEAD -> SoundType.SILENT
                                                SoundType.SILENT -> SoundType.BELL
                                            }
                                            viewModel.setSoundType(nextSound)
                                        },
                                        onToggleTheme = {
                                            val nextTheme = when (uiState.themeMode) {
                                                "light" -> "dark"
                                                "dark" -> "liquid_glass"
                                                "liquid_glass" -> "light"
                                                else -> "liquid_glass"
                                            }
                                            viewModel.setThemeMode(nextTheme)
                                        },
                                        onUpdateSankalpaTarget = { newTarget ->
                                            viewModel.setSankalpaTarget(newTarget)
                                        },
                                        onSelectLanguage = { viewModel.setAppLanguage(it) },
                                        onOpenDhyanMode = { isDhyanModeOpen = true },
                                        onDismissCelebration = { viewModel.dismissCelebration() },
                                        modifier = screenModifier
                                    )
                                }
                                ScreenTab.HISTORY -> {
                                    SadhanaHistoryScreen(
                                        sadhanaList = recentSadhana,
                                        lifetimeBeads = lifetimeBeads,
                                        lifetimeMalas = lifetimeMalas,
                                        targetMalas = uiState.sankalpaTargetMalas,
                                        streakDays = uiState.streakDays,
                                        todayMantraList = todayMantraList,
                                        lifetimeMantraBreakdown = lifetimeMantraBreakdown,
                                        appLanguage = uiState.appLanguage,
                                        themeMode = uiState.themeMode,
                                        modifier = screenModifier
                                    )
                                }
                                ScreenTab.SETTINGS -> {
                                    SankalpaSettingsScreen(
                                        uiState = uiState,
                                        todayMantraList = todayMantraList,
                                        lifetimeMantraBreakdown = lifetimeMantraBreakdown,
                                        onSetSankalpa = { viewModel.setSankalpaTarget(it) },
                                        onSetMantra = { viewModel.setMantra(it) },
                                        onSetSoundType = { viewModel.setSoundType(it) },
                                        onSetHapticStrength = { viewModel.setHapticStrength(it) },
                                        onSetTapAreaMode = { viewModel.setTapAreaMode(it) },
                                        onSetVolumeKeysEnabled = { viewModel.setVolumeKeysEnabled(it) },
                                        onSetThemeMode = { viewModel.setThemeMode(it) },
                                        onSetAppLanguage = { viewModel.setAppLanguage(it) },
                                        onSetHideStatusBar = { viewModel.setHideStatusBar(it) },
                                        modifier = screenModifier
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && viewModel.uiState.value.hideStatusBar) {
            val controller = WindowCompat.getInsetsController(window, window.decorView)
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.hide(WindowInsetsCompat.Type.statusBars())
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (viewModel.uiState.value.volumeKeysEnabled &&
            (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
        ) {
            viewModel.incrementBead()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}

/**
 * Liquid Navigation Item with fluid spring bounce, expanding water droplet wave, and specular pill
 */
@Composable
fun RowScope.LiquidTabItem(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    isLiquid: Boolean,
    testTag: String,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(16.dp)

    val iconScale by animateFloatAsState(
        targetValue = if (selected) 1.12f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessMediumLow),
        label = "tabIconScale"
    )
    val pillAlpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = tween(durationMillis = 240, easing = FastOutSlowInEasing),
        label = "tabPillAlpha"
    )

    Box(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 4.dp)
            .liquidClickable(
                rippleColor = if (isLiquid) Color(0xFF48CAE4) else SacredGoldGlow,
                onClick = onClick
            )
            .clip(shape)
            .background(
                if (isLiquid) Color(0x5548CAE4).copy(alpha = 0.55f * pillAlpha)
                else MaterialTheme.colorScheme.primaryContainer.copy(alpha = pillAlpha)
            )
            .border(
                width = if (selected) 1.2.dp else 0.dp,
                brush = if (selected) {
                    Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.90f),
                            if (isLiquid) Color(0xFF48CAE4) else SacredAmberRadiance,
                            Color.White.copy(alpha = 0.30f)
                        )
                    )
                } else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent)),
                shape = shape
            )
            .padding(vertical = 8.dp)
            .testTag(testTag),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (selected) {
                    if (isLiquid) Color(0xFFFFD54F) else MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer {
                        scaleX = iconScale
                        scaleY = iconScale
                    }
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                    fontSize = 11.sp,
                    color = if (selected) {
                        if (isLiquid) Color(0xFFFFFFFF) else MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            )
        }
    }
}
