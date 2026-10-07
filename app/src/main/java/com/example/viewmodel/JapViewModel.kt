package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.JapSoundPlayer
import com.example.audio.SoundType
import com.example.data.local.AppDatabase
import com.example.data.local.DailySadhana
import com.example.data.local.LifetimeMantraSummary
import com.example.data.local.MantraSadhana
import com.example.data.preferences.HapticStrength
import com.example.data.preferences.TapAreaMode
import com.example.data.preferences.UserPreferences
import com.example.data.repository.JapRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class JapUiState(
    val currentBead: Int = 0, // 0..107 (bead position in current mala for active mantra)
    val currentMantraTodayBeads: Int = 0, // Total beads chanted for active mantra today
    val currentMantraTodayMalas: Int = 0, // Total malas chanted for active mantra today
    val todayMalas: Int = 0, // Overall malas across all mantras today
    val todayTotalBeads: Int = 0, // Overall beads across all mantras today
    val sankalpaTargetMalas: Int = 16,
    val isSankalpaDone: Boolean = false,
    val currentMantra: String = "श्री राधा",
    val soundType: SoundType = SoundType.BELL,
    val hapticStrength: HapticStrength = HapticStrength.MEDIUM,
    val tapAreaMode: TapAreaMode = TapAreaMode.BUTTON_ONLY,
    val volumeKeysEnabled: Boolean = true,
    val isDronePlaying: Boolean = false,
    val showCelebrationDialog: Boolean = false,
    val celebrationMalaCount: Int = 0,
    val celebrationMantraName: String = "श्री राधा",
    val themeMode: String = "liquid_glass",
    val canUndo: Boolean = false,
    val streakDays: Int = 1,
    val appLanguage: com.example.localization.AppLanguage = com.example.localization.AppLanguage.HINDI,
    val hideStatusBar: Boolean = true
)

class JapViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: JapRepository
    private val preferences = UserPreferences(application)
    val soundPlayer = JapSoundPlayer()

    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = application.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        vibratorManager?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        application.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    private val todayDateString: String

    private val _uiState = MutableStateFlow(JapUiState())
    val uiState: StateFlow<JapUiState> = _uiState.asStateFlow()

    // Recent 30 days sadhana for calendar & chart
    val recentSadhana: StateFlow<List<DailySadhana>>

    // Separate breakdown per mantra today & lifetime
    val todayMantraBreakdown: StateFlow<List<MantraSadhana>>
    val lifetimeMantraBreakdown: StateFlow<List<LifetimeMantraSummary>>

    // Lifetime totals
    val lifetimeBeads: StateFlow<Int>
    val lifetimeMalas: StateFlow<Int>

    // Undo stack for accidental clicks
    private val undoHistory = ArrayDeque<Int>()

    init {
        val db = AppDatabase.getInstance(application)
        repository = JapRepository(db.japDao())
        todayDateString = repository.getTodayDateString()

        // Backfill mantra sadhana from existing logs if empty
        viewModelScope.launch {
            repository.backfillMantraSadhanaIfNeeded()
        }

        recentSadhana = repository.getRecentDailySadhanaFlow().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        todayMantraBreakdown = repository.getTodayMantraSadhanaFlow(todayDateString).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        lifetimeMantraBreakdown = repository.getLifetimeMantraBreakdownFlow().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        lifetimeBeads = repository.getTotalLifetimeBeadsFlow().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

        lifetimeMalas = repository.getTotalLifetimeMalasFlow().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

        // Load preferences into initial state
        val initialMantra = preferences.selectedMantra
        _uiState.update {
            it.copy(
                currentMantra = initialMantra,
                celebrationMantraName = initialMantra,
                soundType = preferences.soundType,
                hapticStrength = preferences.hapticStrength,
                tapAreaMode = preferences.tapAreaMode,
                volumeKeysEnabled = preferences.volumeKeysEnabled,
                sankalpaTargetMalas = preferences.defaultSankalpaMalas,
                themeMode = preferences.darkThemeMode,
                appLanguage = preferences.appLanguage,
                hideStatusBar = preferences.hideStatusBar
            )
        }

        // Observe today's overall daily sadhana from Room DB
        viewModelScope.launch {
            repository.getDailySadhanaFlow(todayDateString).collect { sadhana ->
                if (sadhana != null) {
                    val completedMalas = sadhana.totalBeads / 108
                    _uiState.update { current ->
                        current.copy(
                            todayMalas = completedMalas,
                            todayTotalBeads = sadhana.totalBeads,
                            sankalpaTargetMalas = sadhana.sankalpaTargetMalas,
                            isSankalpaDone = completedMalas >= sadhana.sankalpaTargetMalas
                        )
                    }
                }
            }
        }

        // Observe mantra-specific sadhana to keep active mantra count accurate
        viewModelScope.launch {
            todayMantraBreakdown.collect { list ->
                val activeMantra = _uiState.value.currentMantra
                val existing = list.find { it.mantraName == activeMantra }
                val beads = existing?.totalBeads ?: 0
                val malas = beads / 108
                val beadInMala = beads % 108
                _uiState.update { current ->
                    current.copy(
                        currentMantraTodayBeads = beads,
                        currentMantraTodayMalas = malas,
                        currentBead = beadInMala
                    )
                }
            }
        }

        // Calculate streak
        viewModelScope.launch {
            repository.getRecentDailySadhanaFlow().collect { list ->
                val streak = calculateStreak(list)
                _uiState.update { it.copy(streakDays = streak) }
            }
        }
    }

    private fun calculateStreak(list: List<DailySadhana>): Int {
        if (list.isEmpty()) return 1
        var count = 0
        for (item in list) {
            if (item.totalBeads > 0) {
                count++
            } else {
                break
            }
        }
        return count.coerceAtLeast(1)
    }

    private var lastIncrementTimestamp = 0L

    /**
     * Primary action: Devotee chants and increments one bead count
     */
    fun incrementBead() {
        val now = System.currentTimeMillis()
        if (now - lastIncrementTimestamp < 180L) {
            // Guard against accidental double clicks or touch bubbling within 180ms
            return
        }
        lastIncrementTimestamp = now

        val currentState = _uiState.value
        val newCurrentMantraBeads = currentState.currentMantraTodayBeads + 1
        val newCurrentMantraMalas = newCurrentMantraBeads / 108
        val newCurrentBead = newCurrentMantraBeads % 108
        val isMalaComplete = (newCurrentBead == 0 && newCurrentMantraBeads > 0)

        undoHistory.addLast(1)
        if (undoHistory.size > 20) undoHistory.removeFirst()

        // Haptic feedback
        performHaptic(isMalaComplete = isMalaComplete)

        // Sound feedback
        if (isMalaComplete) {
            soundPlayer.playMalaCompleteCelebration()
        } else {
            soundPlayer.playSound(currentState.soundType)
        }

        val newTodayTotalBeads = currentState.todayTotalBeads + 1
        val newTodayMalas = newTodayTotalBeads / 108

        if (isMalaComplete) {
            // Completed 108 beads of this specific Mantra! One full Mala!
            _uiState.update {
                it.copy(
                    currentBead = 0,
                    currentMantraTodayBeads = newCurrentMantraBeads,
                    currentMantraTodayMalas = newCurrentMantraMalas,
                    todayMalas = newTodayMalas,
                    todayTotalBeads = newTodayTotalBeads,
                    showCelebrationDialog = true,
                    celebrationMalaCount = newCurrentMantraMalas,
                    celebrationMantraName = currentState.currentMantra,
                    isSankalpaDone = newTodayMalas >= currentState.sankalpaTargetMalas,
                    canUndo = true
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    currentBead = newCurrentBead,
                    currentMantraTodayBeads = newCurrentMantraBeads,
                    currentMantraTodayMalas = newCurrentMantraMalas,
                    todayTotalBeads = newTodayTotalBeads,
                    todayMalas = newTodayMalas,
                    canUndo = true
                )
            }
        }

        // Save progress reactively into Room Database
        viewModelScope.launch {
            repository.addJapCount(
                dateString = todayDateString,
                mantraName = currentState.currentMantra,
                beadsCount = 1,
                targetMalas = currentState.sankalpaTargetMalas
            )
        }
    }

    fun undoLastBead() {
        if (undoHistory.isEmpty()) return
        undoHistory.removeLast()

        val currentState = _uiState.value
        if (currentState.currentMantraTodayBeads <= 0 && currentState.todayTotalBeads <= 0) return

        val newMantraBeads = (currentState.currentMantraTodayBeads - 1).coerceAtLeast(0)
        val newTotalBeads = (currentState.todayTotalBeads - 1).coerceAtLeast(0)
        val newBead = newMantraBeads % 108
        val newMantraMalas = newMantraBeads / 108
        val newTodayMalas = newTotalBeads / 108

        _uiState.update {
            it.copy(
                currentBead = newBead,
                currentMantraTodayBeads = newMantraBeads,
                currentMantraTodayMalas = newMantraMalas,
                todayMalas = newTodayMalas,
                todayTotalBeads = newTotalBeads,
                canUndo = undoHistory.isNotEmpty()
            )
        }

        viewModelScope.launch {
            repository.addJapCount(
                dateString = todayDateString,
                mantraName = currentState.currentMantra,
                beadsCount = -1,
                targetMalas = currentState.sankalpaTargetMalas
            )
        }
    }

    fun resetCurrentMala() {
        val currentState = _uiState.value
        val beadsInCurrentMala = currentState.currentBead
        if (beadsInCurrentMala <= 0) return

        val newMantraBeads = (currentState.currentMantraTodayBeads - beadsInCurrentMala).coerceAtLeast(0)
        val newTotalBeads = (currentState.todayTotalBeads - beadsInCurrentMala).coerceAtLeast(0)
        val newTodayMalas = newTotalBeads / 108

        _uiState.update {
            it.copy(
                currentBead = 0,
                currentMantraTodayBeads = newMantraBeads,
                currentMantraTodayMalas = newMantraBeads / 108,
                todayTotalBeads = newTotalBeads,
                todayMalas = newTodayMalas
            )
        }

        viewModelScope.launch {
            repository.addJapCount(
                dateString = todayDateString,
                mantraName = currentState.currentMantra,
                beadsCount = -beadsInCurrentMala,
                targetMalas = currentState.sankalpaTargetMalas
            )
        }
    }

    fun dismissCelebration() {
        _uiState.update { it.copy(showCelebrationDialog = false) }
    }

    fun setMantra(mantra: String) {
        preferences.selectedMantra = mantra
        val existing = todayMantraBreakdown.value.find { it.mantraName == mantra }
        val beads = existing?.totalBeads ?: 0
        val malas = beads / 108
        val beadInMala = beads % 108
        _uiState.update {
            it.copy(
                currentMantra = mantra,
                celebrationMantraName = mantra,
                currentMantraTodayBeads = beads,
                currentMantraTodayMalas = malas,
                currentBead = beadInMala
            )
        }
    }

    fun setSoundType(soundType: SoundType) {
        preferences.soundType = soundType
        _uiState.update { it.copy(soundType = soundType) }
        soundPlayer.playSound(soundType)
    }

    fun setHapticStrength(strength: HapticStrength) {
        preferences.hapticStrength = strength
        _uiState.update { it.copy(hapticStrength = strength) }
        performHaptic(isMalaComplete = false)
    }

    fun setTapAreaMode(mode: TapAreaMode) {
        preferences.tapAreaMode = mode
        _uiState.update { it.copy(tapAreaMode = mode) }
    }

    fun setVolumeKeysEnabled(enabled: Boolean) {
        preferences.volumeKeysEnabled = enabled
        _uiState.update { it.copy(volumeKeysEnabled = enabled) }
    }

    fun setSankalpaTarget(targetMalas: Int) {
        val safeTarget = targetMalas.coerceAtLeast(1)
        preferences.defaultSankalpaMalas = safeTarget
        _uiState.update {
            it.copy(
                sankalpaTargetMalas = safeTarget,
                isSankalpaDone = it.todayMalas >= safeTarget
            )
        }
        viewModelScope.launch {
            repository.updateSankalpaTarget(todayDateString, safeTarget)
        }
    }

    fun setThemeMode(mode: String) {
        preferences.darkThemeMode = mode
        _uiState.update { it.copy(themeMode = mode) }
    }

    fun setAppLanguage(language: com.example.localization.AppLanguage) {
        preferences.appLanguage = language
        val currentMantra = _uiState.value.currentMantra
        val oldLang = _uiState.value.appLanguage
        val oldPresets = com.example.localization.LanguageProvider.getStrings(oldLang).presetMantras
        val newPresets = com.example.localization.LanguageProvider.getStrings(language).presetMantras
        val index = oldPresets.indexOf(currentMantra)

        val updatedMantra = if (index >= 0 && index < newPresets.size) {
            newPresets[index]
        } else if (currentMantra == "श्री राधा" || currentMantra == "Sri Radha") {
            newPresets.firstOrNull() ?: currentMantra
        } else {
            currentMantra
        }

        if (updatedMantra != currentMantra) {
            preferences.selectedMantra = updatedMantra
        }

        _uiState.update {
            it.copy(
                appLanguage = language,
                currentMantra = updatedMantra,
                celebrationMantraName = updatedMantra
            )
        }
    }

    fun setHideStatusBar(hide: Boolean) {
        preferences.hideStatusBar = hide
        _uiState.update { it.copy(hideStatusBar = hide) }
    }

    fun toggleDrone() {
        soundPlayer.startAmbientDrone { isPlaying ->
            _uiState.update { it.copy(isDronePlaying = isPlaying) }
        }
    }

    fun performHaptic(isMalaComplete: Boolean) {
        val strength = _uiState.value.hapticStrength
        if (strength == HapticStrength.OFF) return
        val vib = vibrator ?: return

        try {
            if (!vib.hasVibrator()) return

            if (isMalaComplete) {
                // Sacred completion pattern: celebratory double pulse
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    try {
                        vib.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK))
                        return
                    } catch (_: Exception) {}
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val timings = longArrayOf(0, 120, 80, 240)
                    vib.vibrate(VibrationEffect.createWaveform(timings, -1))
                } else {
                    @Suppress("DEPRECATION")
                    vib.vibrate(longArrayOf(0, 120, 80, 240), -1)
                }
            } else {
                // Bead tap: instant tactile confirmation
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val effectId = when (strength) {
                        HapticStrength.GENTLE -> VibrationEffect.EFFECT_TICK
                        HapticStrength.MEDIUM -> VibrationEffect.EFFECT_CLICK
                        HapticStrength.STRONG -> VibrationEffect.EFFECT_HEAVY_CLICK
                        HapticStrength.OFF -> null
                    }
                    if (effectId != null) {
                        try {
                            vib.vibrate(VibrationEffect.createPredefined(effectId))
                            return
                        } catch (_: Exception) {}
                    }
                }

                val durationMs = when (strength) {
                    HapticStrength.GENTLE -> 25L
                    HapticStrength.MEDIUM -> 50L
                    HapticStrength.STRONG -> 90L
                    HapticStrength.OFF -> 0L
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val amplitude = when (strength) {
                        HapticStrength.GENTLE -> 110
                        HapticStrength.MEDIUM -> 190
                        HapticStrength.STRONG -> 255
                        HapticStrength.OFF -> 0
                    }
                    val effect = if (vib.hasAmplitudeControl()) {
                        VibrationEffect.createOneShot(durationMs, amplitude)
                    } else {
                        VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE)
                    }
                    vib.vibrate(effect)
                } else {
                    @Suppress("DEPRECATION")
                    vib.vibrate(durationMs)
                }
            }
        } catch (_: Exception) {
            // Graceful fallback
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundPlayer.release()
    }
}
