package com.example.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.audio.SoundType

enum class HapticStrength(val title: String) {
    OFF("बंद (Off)"),
    GENTLE("हल्का (Gentle)"),
    MEDIUM("मध्यम (Medium)"),
    STRONG("तीव्र (Strong)")
}

enum class TapAreaMode(val title: String) {
    BUTTON_ONLY("केवल जप बटन (Center Button)"),
    FULL_SCREEN("पूरी स्क्रीन पर स्पर्श (Tap Anywhere)")
}

class UserPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("radha_jap_prefs", Context.MODE_PRIVATE)

    var soundType: SoundType
        get() {
            val name = prefs.getString("sound_type", SoundType.BELL.name) ?: SoundType.BELL.name
            return try { SoundType.valueOf(name) } catch (_: Exception) { SoundType.BELL }
        }
        set(value) = prefs.edit().putString("sound_type", value.name).apply()

    var hapticStrength: HapticStrength
        get() {
            val name = prefs.getString("haptic_strength", HapticStrength.MEDIUM.name) ?: HapticStrength.MEDIUM.name
            return try { HapticStrength.valueOf(name) } catch (_: Exception) { HapticStrength.MEDIUM }
        }
        set(value) = prefs.edit().putString("haptic_strength", value.name).apply()

    var tapAreaMode: TapAreaMode
        get() {
            val name = prefs.getString("tap_area_mode", TapAreaMode.BUTTON_ONLY.name) ?: TapAreaMode.BUTTON_ONLY.name
            return try { TapAreaMode.valueOf(name) } catch (_: Exception) { TapAreaMode.BUTTON_ONLY }
        }
        set(value) = prefs.edit().putString("tap_area_mode", value.name).apply()

    var defaultSankalpaMalas: Int
        get() = prefs.getInt("sankalpa_malas", 16)
        set(value) = prefs.edit().putInt("sankalpa_malas", value).apply()

    var volumeKeysEnabled: Boolean
        get() = prefs.getBoolean("volume_keys_enabled", true)
        set(value) = prefs.edit().putBoolean("volume_keys_enabled", value).apply()

    var selectedMantra: String
        get() = prefs.getString("selected_mantra", "श्री राधा") ?: "श्री राधा"
        set(value) = prefs.edit().putString("selected_mantra", value).apply()

    var darkThemeMode: String // "liquid_glass", "light", "dark", "system"
        get() = prefs.getString("theme_mode", "liquid_glass") ?: "liquid_glass"
        set(value) = prefs.edit().putString("theme_mode", value).apply()

    var keepScreenAwake: Boolean
        get() = prefs.getBoolean("keep_screen_awake", true)
        set(value) = prefs.edit().putBoolean("keep_screen_awake", value).apply()

    var appLanguage: com.example.localization.AppLanguage
        get() {
            val code = prefs.getString("app_language_code", com.example.localization.AppLanguage.HINDI.code) ?: com.example.localization.AppLanguage.HINDI.code
            return com.example.localization.AppLanguage.fromCode(code)
        }
        set(value) = prefs.edit().putString("app_language_code", value.code).apply()

    var hideStatusBar: Boolean
        get() = prefs.getBoolean("hide_status_bar", true)
        set(value) = prefs.edit().putBoolean("hide_status_bar", value).apply()
}
