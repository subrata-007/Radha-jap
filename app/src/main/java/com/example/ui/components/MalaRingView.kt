package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.ui.theme.LiquidGlassBackground
import com.example.ui.theme.SacredAmberRadiance
import com.example.ui.theme.SacredGoldGlow
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

// Precomputed 108 bead positions for zero-allocation 120 FPS render loop
private const val TOTAL_BEADS = 108
private val PRECOMPUTED_COS = FloatArray(TOTAL_BEADS) { i ->
    cos((-PI / 2.0) + (i.toDouble() / TOTAL_BEADS * 2.0 * PI)).toFloat()
}
private val PRECOMPUTED_SIN = FloatArray(TOTAL_BEADS) { i ->
    sin((-PI / 2.0) + (i.toDouble() / TOTAL_BEADS * 2.0 * PI)).toFloat()
}

/**
 * 108 Mala Beads Rosary Ring with Sumeru Master Bead.
 * Liquid Glass styling features shining crystal dewdrop beads with 3D specular reflections,
 * dual caustic highlights, and ethereal celestial progress arc.
 */
@Composable
fun MalaRingView(
    currentBead: Int, // 0..107
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    // Ultra-smooth physical spring for continuous progress flow
    val animatedProgress by animateFloatAsState(
        targetValue = currentBead.toFloat() / TOTAL_BEADS,
        animationSpec = spring(dampingRatio = 0.72f, stiffness = 320f),
        label = "malaProgress"
    )

    // Gentle spiritual pulse for active bead and Sumeru halo
    val infiniteTransition = rememberInfiniteTransition(label = "malaAuraPulse")
    val activeBeadPulse by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.18f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "activeBeadPulse"
    )
    val sumeruPulse by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.14f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sumeruPulse"
    )

    val isLiquid = MaterialTheme.colorScheme.background == LiquidGlassBackground

    val activeColor = if (isLiquid) Color(0xFF48CAE4) else SacredAmberRadiance
    val activeSecondary = if (isLiquid) Color(0xFFFFD166) else SacredAmberRadiance
    val inactiveColor = if (isLiquid) Color(0x70FFFFFF) else SacredGoldGlow.copy(alpha = 0.55f)
    val glowColor = if (isLiquid) Color(0xFF00B4D8).copy(alpha = 0.85f) else SacredGoldGlow.copy(alpha = 0.75f)
    val sumeruColor = if (isLiquid) Color(0xFFFFD166) else SacredGoldGlow

    Box(
        modifier = modifier
            .size(310.dp)
            .graphicsLayer {
                clip = false
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = (size.minDimension / 2f) - 18.dp.toPx()

            // Subtle background track ring (Shimmering continuous crystal glass filament)
            drawCircle(
                brush = if (isLiquid) {
                    Brush.sweepGradient(
                        listOf(
                            Color(0x6648CAE4),
                            Color(0x66FFD166),
                            Color(0x66C77DFF),
                            Color(0x6648CAE4)
                        ),
                        center = center
                    )
                } else {
                    Brush.linearGradient(
                        listOf(SacredGoldGlow.copy(alpha = 0.45f), SacredAmberRadiance.copy(alpha = 0.45f))
                    )
                },
                radius = radius,
                center = center,
                style = Stroke(width = 2.5.dp.toPx())
            )

            // Progress arc glow (Celestial glowing water filament)
            if (animatedProgress > 0f) {
                drawArc(
                    brush = if (isLiquid) {
                        Brush.sweepGradient(
                            listOf(
                                Color(0xFF00B4D8).copy(alpha = 0.45f),
                                Color(0xFF48CAE4),
                                Color(0xFFFFD166)
                            ),
                            center = center
                        )
                    } else {
                        Brush.linearGradient(listOf(activeColor, SacredGoldGlow))
                    },
                    startAngle = -90f,
                    sweepAngle = animatedProgress * 360f,
                    useCenter = false,
                    style = Stroke(width = 7.dp.toPx(), cap = StrokeCap.Round),
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2)
                )
            }

            // Draw 108 beads along the circle using pre-computed vectors
            for (i in 0 until TOTAL_BEADS) {
                val bx = center.x + (radius * PRECOMPUTED_COS[i])
                val by = center.y + (radius * PRECOMPUTED_SIN[i])

                val isCompleted = i < currentBead
                val isCurrentActive = i == currentBead

                when {
                    isCurrentActive -> {
                        // Current active bead: radiant golden-cyan liquid pearl with animated pulse halo
                        val pulsedRadius = 9.5.dp.toPx() * activeBeadPulse
                        val coreRadius = 6.2.dp.toPx() * (1f + (activeBeadPulse - 1f) * 0.4f)
                        drawCircle(
                            color = glowColor.copy(alpha = (0.75f * activeBeadPulse).coerceIn(0.5f, 0.95f)),
                            radius = pulsedRadius,
                            center = Offset(bx, by)
                        )
                        drawCircle(
                            brush = if (isLiquid) {
                                Brush.radialGradient(
                                    listOf(Color(0xFFFFFFFF), activeSecondary, Color(0xFF00B4D8)),
                                    center = Offset(bx - 1f, by - 1f),
                                    radius = coreRadius
                                )
                            } else {
                                Brush.radialGradient(listOf(SacredGoldGlow, activeColor), center = Offset(bx, by), radius = coreRadius)
                            },
                            radius = coreRadius,
                            center = Offset(bx, by)
                        )
                        // Primary top-left specular pinpoint glint
                        drawCircle(
                            color = Color.White,
                            radius = 2.6.dp.toPx(),
                            center = Offset(bx - 1.8f, by - 1.8f)
                        )
                        // Secondary bottom-right caustic reflection
                        if (isLiquid) {
                            drawCircle(
                                color = Color(0xEEFFD166),
                                radius = 1.4.dp.toPx(),
                                center = Offset(bx + 1.8f, by + 1.8f)
                            )
                        }
                    }
                    isCompleted -> {
                        // Completed beads: crystalline radiant aqua water dewdrops
                        drawCircle(
                            color = if (isLiquid) Color(0xFF00E5FF) else activeColor,
                            radius = 4.2.dp.toPx(),
                            center = Offset(bx, by)
                        )
                        // Primary top specular glint
                        drawCircle(
                            color = Color.White,
                            radius = 1.6.dp.toPx(),
                            center = Offset(bx - 1.0f, by - 1.0f)
                        )
                        if (isLiquid) {
                            // Bottom bounce refraction
                            drawCircle(
                                color = Color(0xCCFFD166),
                                radius = 1.0.dp.toPx(),
                                center = Offset(bx + 1.0f, by + 1.0f)
                            )
                        }
                    }
                    else -> {
                        // Uncompleted beads: radiant warm golden-amber pearls with pure white crystal sheen
                        drawCircle(
                            color = if (isLiquid) Color(0xFFFFD166).copy(alpha = 0.85f) else SacredGoldGlow.copy(alpha = 0.80f),
                            radius = 3.2.dp.toPx(),
                            center = Offset(bx, by)
                        )
                        drawCircle(
                            color = Color.White.copy(alpha = 0.95f),
                            radius = 1.4.dp.toPx(),
                            center = Offset(bx - 0.8f, by - 0.8f)
                        )
                    }
                }
            }

            // Sumeru Master Bead at the very top (index 0)
            val sumeruX = center.x
            val sumeruY = center.y - radius

            // Sumeru sacred lotus aura halo
            drawCircle(
                color = if (isLiquid) Color(0x88FFD166) else sumeruColor.copy(alpha = 0.55f),
                radius = 14.dp.toPx() * sumeruPulse,
                center = Offset(sumeruX, sumeruY)
            )
            if (isLiquid) {
                drawCircle(
                    color = Color(0x4448CAE4),
                    radius = 18.dp.toPx() * sumeruPulse,
                    center = Offset(sumeruX, sumeruY)
                )
            }
            // Sumeru bead body
            drawCircle(
                brush = if (isLiquid) {
                    Brush.radialGradient(
                        listOf(Color(0xFFFFF0B8), sumeruColor, Color(0xFFD49A00)),
                        center = Offset(sumeruX - 2f, sumeruY - 2f),
                        radius = 8.5.dp.toPx()
                    )
                } else {
                    Brush.radialGradient(listOf(SacredGoldGlow, sumeruColor), center = Offset(sumeruX, sumeruY), radius = 8.5.dp.toPx())
                },
                radius = 8.5.dp.toPx(),
                center = Offset(sumeruX, sumeruY)
            )
            // Top diamond specular shine
            drawCircle(
                color = Color.White,
                radius = 3.6.dp.toPx(),
                center = Offset(sumeruX - 2.0f, sumeruY - 2.0f)
            )
        }

        // Central content (Jap button & text)
        content()
    }
}
