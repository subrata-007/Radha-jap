package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sin

enum class SoundType(val title: String) {
    BELL("घंटी (Temple Bell)"),
    FLUTE("बांसुरी (Flute)"),
    SHANKH("शंख (Shankh)"),
    TULSI_BEAD("तुलसी मनका (Tulsi Bead)"),
    SILENT("मौन (Silent)")
}

/**
 * Ultra-Low Latency Sacred Audio Engine for Radha Jap
 * Pre-computes authentic acoustic waveforms and uses static AudioTracks
 * to provide instantaneous, zero-delay (<1ms) audio feedback on bead touches.
 */
class JapSoundPlayer {

    private val sampleRate = 44100
    private var ambientDroneJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    // Pre-computed audio buffers for zero runtime synthesis overhead
    private val bellBuffer: ShortArray by lazy { synthesizeTempleBell() }
    private val fluteBuffer: ShortArray by lazy { synthesizeDivineBansuri() }
    private val shankhBuffer: ShortArray by lazy { synthesizeSacredShankh() }
    private val tulsiBuffer: ShortArray by lazy { synthesizeTulsiBeadClick() }
    private val celebrationBuffer: ShortArray by lazy { synthesizeCelebrationFanfare() }

    // Pre-loaded low-latency static AudioTracks
    private var bellTrack: AudioTrack? = null
    private var fluteTrack: AudioTrack? = null
    private var shankhTrack: AudioTrack? = null
    private var tulsiTrack: AudioTrack? = null
    private var celebrationTrack: AudioTrack? = null

    init {
        // Asynchronously warm up the pre-rendered audio buffers and static tracks
        scope.launch {
            bellTrack = initStaticTrack(bellBuffer)
            fluteTrack = initStaticTrack(fluteBuffer)
            shankhTrack = initStaticTrack(shankhBuffer)
            tulsiTrack = initStaticTrack(tulsiBuffer)
            celebrationTrack = initStaticTrack(celebrationBuffer)
        }
    }

    private fun initStaticTrack(buffer: ShortArray): AudioTrack? {
        return try {
            val minBufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )
            val bufferSize = (buffer.size * 2).coerceAtLeast(minBufferSize)

            val track = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            track.write(buffer, 0, buffer.size)
            track
        } catch (_: Exception) {
            null
        }
    }

    private fun triggerStaticTrack(track: AudioTrack?, fallbackBuffer: ShortArray) {
        if (track != null) {
            try {
                if (track.playState == AudioTrack.PLAYSTATE_PLAYING) {
                    track.stop()
                }
                track.reloadStaticData()
                track.play()
                return
            } catch (_: Exception) {}
        }

        // Fallback dynamic playback if static track is unavailable
        scope.launch {
            try {
                val tempTrack = initStaticTrack(fallbackBuffer)
                tempTrack?.play()
            } catch (_: Exception) {}
        }
    }

    /**
     * Synthesize authentic Indian temple brass bell (Ghanta)
     * Rich bronze inharmonic partials: Hum tone (261Hz), Prime (523Hz),
     * Tierce (622Hz), Quint (784Hz) and sparkling metallic shimmer.
     */
    private fun synthesizeTempleBell(): ShortArray {
        val durationSec = 0.50
        val numSamples = (sampleRate * durationSec).toInt()
        val buffer = ShortArray(numSamples)

        val f0 = 523.25 // C5 prime strike
        val fHum = f0 * 0.5 // Sub-octave hum tone
        val fTierce = f0 * 1.189 // Minor third
        val fQuint = f0 * 1.498 // Fifth
        val fNominal = f0 * 2.0 // Octave
        val fShimmer = f0 * 3.12 // Inharmonic metallic sparkle

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            // 3ms smooth fade-in to eliminate click
            val attack = (t / 0.003).coerceAtMost(1.0)

            // Damping rates: high partials die quickly, warm fundamental lingers
            val envHum = exp(-3.8 * t)
            val envPrime = exp(-4.6 * t)
            val envTierce = exp(-6.5 * t)
            val envQuint = exp(-8.5 * t)
            val envShimmer = exp(-15.0 * t)

            val sample = attack * (
                0.28 * sin(2.0 * PI * fHum * t) * envHum +
                0.46 * sin(2.0 * PI * f0 * t) * envPrime +
                0.22 * sin(2.0 * PI * fTierce * t) * envTierce +
                0.16 * sin(2.0 * PI * fQuint * t) * envQuint +
                0.09 * sin(2.0 * PI * fNominal * t) * envQuint +
                0.06 * sin(2.0 * PI * fShimmer * t) * envShimmer
            )

            buffer[i] = (sample * Short.MAX_VALUE * 0.88).toInt().coerceIn(-32768, 32767).toShort()
        }
        return buffer
    }

    /**
     * Synthesize divine bamboo bansuri (Krishna's flute)
     * Sweet breath onset, warm odd harmonics, and natural delayed vibrato.
     */
    private fun synthesizeDivineBansuri(): ShortArray {
        val durationSec = 0.38
        val numSamples = (sampleRate * durationSec).toInt()
        val buffer = ShortArray(numSamples)

        val baseFreq = 587.33 // D5 tonal center (Raga Bhupali)

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            // 20ms gentle breath attack, smooth rounded release
            val attack = (t / 0.020).coerceAtMost(1.0)
            val release = ((durationSec - t) / 0.08).coerceIn(0.0, 1.0)
            val envelope = attack * release

            // Delayed sweet vibrato swells naturally after 50ms
            val vibratoAmp = ((t - 0.05) / 0.15).coerceIn(0.0, 1.0) * 4.2
            val vibrato = vibratoAmp * sin(2.0 * PI * 5.2 * t)

            // Bamboo harmonic spectrum
            val sample = envelope * (
                0.78 * sin(2.0 * PI * (baseFreq + vibrato) * t) +
                0.20 * sin(2.0 * PI * (baseFreq * 2.0 + vibrato) * t) +
                0.08 * sin(2.0 * PI * (baseFreq * 3.0) * t)
            )

            buffer[i] = (sample * Short.MAX_VALUE * 0.82).toInt().coerceIn(-32768, 32767).toShort()
        }
        return buffer
    }

    /**
     * Synthesize sacred Shankh (conch shell) tone
     * Warm brassy resonance with swelling attack and deep spiritual vibration.
     */
    private fun synthesizeSacredShankh(): ShortArray {
        val durationSec = 0.42
        val numSamples = (sampleRate * durationSec).toInt()
        val buffer = ShortArray(numSamples)

        val baseFreq = 261.63 // C4 sacred resonance

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val attack = (t / 0.045).coerceAtMost(1.0)
            val decay = exp(-3.6 * t)
            val envelope = attack * decay

            // Brass lip vibration with subtle warm growl
            val growl = 1.0 + 0.04 * sin(2.0 * PI * 18.0 * t)
            val sample = envelope * (
                0.52 * sin(2.0 * PI * baseFreq * t * growl) +
                0.32 * sin(2.0 * PI * (baseFreq * 2.0) * t) +
                0.16 * sin(2.0 * PI * (baseFreq * 3.0) * t) +
                0.08 * sin(2.0 * PI * (baseFreq * 4.0) * t)
            )

            buffer[i] = (sample * Short.MAX_VALUE * 0.85).toInt().coerceIn(-32768, 32767).toShort()
        }
        return buffer
    }

    /**
     * Synthesize organic Tulsi wood bead click
     * Hollow wooden resonance simulating sacred sandalwood / tulsi japa beads.
     */
    private fun synthesizeTulsiBeadClick(): ShortArray {
        val durationSec = 0.14
        val numSamples = (sampleRate * durationSec).toInt()
        val buffer = ShortArray(numSamples)

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val attack = (t / 0.002).coerceAtMost(1.0)

            // Rapidly damped hollow wooden resonances
            val sample = attack * (
                0.55 * sin(2.0 * PI * 780.0 * t) * exp(-52.0 * t) +
                0.35 * sin(2.0 * PI * 1420.0 * t) * exp(-68.0 * t) +
                0.30 * sin(2.0 * PI * 260.0 * t) * exp(-32.0 * t)
            )

            buffer[i] = (sample * Short.MAX_VALUE * 0.90).toInt().coerceIn(-32768, 32767).toShort()
        }
        return buffer
    }

    /**
     * Synthesize grand 108-Mala completion celebration fanfare
     * Harmonious blend of sacred conch call resolving into a shimmering temple chime triad.
     */
    private fun synthesizeCelebrationFanfare(): ShortArray {
        val durationSec = 1.6
        val numSamples = (sampleRate * durationSec).toInt()
        val buffer = ShortArray(numSamples)

        val c5 = 523.25
        val e5 = 659.25
        val g5 = 783.99
        val c6 = 1046.50

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val attack = (t / 0.004).coerceAtMost(1.0)

            // Shimmering sacred triad harmony
            val envBass = exp(-2.4 * t)
            val envMid = exp(-3.0 * t)
            val envHigh = exp(-4.2 * t)

            val sample = attack * (
                0.32 * sin(2.0 * PI * c5 * t) * envBass +
                0.26 * sin(2.0 * PI * e5 * t) * envMid +
                0.24 * sin(2.0 * PI * g5 * t) * envMid +
                0.20 * sin(2.0 * PI * c6 * t) * envHigh +
                0.08 * sin(2.0 * PI * (c6 * 1.5) * t) * exp(-7.0 * t)
            )

            buffer[i] = (sample * Short.MAX_VALUE * 0.88).toInt().coerceIn(-32768, 32767).toShort()
        }
        return buffer
    }

    fun playBell() {
        triggerStaticTrack(bellTrack, bellBuffer)
    }

    fun playFlute() {
        triggerStaticTrack(fluteTrack, fluteBuffer)
    }

    fun playShankh() {
        triggerStaticTrack(shankhTrack, shankhBuffer)
    }

    fun playTulsiBead() {
        triggerStaticTrack(tulsiTrack, tulsiBuffer)
    }

    fun playMalaCompleteCelebration() {
        triggerStaticTrack(celebrationTrack, celebrationBuffer)
    }

    fun playSound(type: SoundType) {
        when (type) {
            SoundType.BELL -> playBell()
            SoundType.FLUTE -> playFlute()
            SoundType.SHANKH -> playShankh()
            SoundType.TULSI_BEAD -> playTulsiBead()
            SoundType.SILENT -> { /* No-op */ }
        }
    }

    /**
     * Starts continuous meditative Tanpura drone in background
     * Authentic 4-string Indian classical tanpura drone (Pa - Sa - Sa - Sa)
     */
    fun startAmbientDrone(onToggleState: (Boolean) -> Unit) {
        if (ambientDroneJob != null) {
            stopAmbientDrone()
            onToggleState(false)
            return
        }

        ambientDroneJob = scope.launch {
            try {
                onToggleState(true)
                val durationSec = 3.0
                val numSamples = (sampleRate * durationSec).toInt()
                val buffer = ShortArray(numSamples)

                val saLow = 136.10 // Kharaj Sa (Om frequency)
                val pa = 204.15 // Pancham
                val saMid1 = 272.20 // Madhya Sa
                val saMid2 = 272.85 // Madhya Sa (subtle acoustic chorus beating)

                for (i in 0 until numSamples) {
                    val t = i.toDouble() / sampleRate
                    // Gentle wave cycle plucks
                    val pluck1 = 0.35 * sin(2.0 * PI * pa * t) * (0.8 + 0.2 * cos(2.0 * PI * 0.33 * t))
                    val pluck2 = 0.40 * sin(2.0 * PI * saMid1 * t) * (0.8 + 0.2 * sin(2.0 * PI * 0.50 * t))
                    val pluck3 = 0.30 * sin(2.0 * PI * saMid2 * t)
                    val droneBass = 0.38 * sin(2.0 * PI * saLow * t)

                    val sample = (pluck1 + pluck2 + pluck3 + droneBass) * 0.22
                    buffer[i] = (sample * Short.MAX_VALUE).toInt().toShort()
                }

                val minBufferSize = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )
                val track = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(buffer.size * 2.coerceAtLeast(minBufferSize))
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()

                track.play()
                while (isActive) {
                    track.write(buffer, 0, buffer.size)
                }
                try {
                    track.stop()
                    track.release()
                } catch (_: Exception) {}
            } catch (e: Exception) {
                onToggleState(false)
            }
        }
    }

    fun stopAmbientDrone() {
        ambientDroneJob?.cancel()
        ambientDroneJob = null
    }

    fun release() {
        stopAmbientDrone()
        try { bellTrack?.release() } catch (_: Exception) {}
        try { fluteTrack?.release() } catch (_: Exception) {}
        try { shankhTrack?.release() } catch (_: Exception) {}
        try { tulsiTrack?.release() } catch (_: Exception) {}
        try { celebrationTrack?.release() } catch (_: Exception) {}
    }
}
