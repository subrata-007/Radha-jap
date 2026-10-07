package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LiquidGlassBackground
import com.example.ui.theme.SacredAmberRadiance
import com.example.ui.theme.SacredGoldGlow
import com.example.ui.theme.SaffronPrimary
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin

/**
 * Ultra-Glossy Liquid Glass Breathing Jap Button
 * Features:
 * - High-Contrast Razor-Sharp Text Visibility across all themes
 * - Viscous Liquid Elastic Bounce Animation
 * - Concentric Liquid Droplet Wavefront Expansion
 * - 3D Convex Specular Rim Glint & Meniscus
 * - Sustained 120 FPS GPU RenderThread animations
 */
@Composable
fun BreathingJapButton(
    currentBead: Int,
    mantraText: String,
    onTap: () -> Unit,
    tapPrompt: String = "स्पर्श करें (Tap)",
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Smooth press feedback scale (interpolated on RenderThread)
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "pressScale"
    )

    // Viscous Liquid Elastic Bounce Animation (0f -> 1f -> 0f spring)
    val liquidBounce = remember { Animatable(0f) }
    // Expanding Liquid Droplet Wavefront Ripple
    val rippleProgress = remember { Animatable(0f) }

    // Breathing halo animation (3.6s gentle meditative prana cycle)
    val infiniteTransition = rememberInfiniteTransition(label = "breathTransition")
    val breathScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.07f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathScale"
    )
    val breathAlpha by infiniteTransition.animateFloat(
        initialValue = 0.32f,
        targetValue = 0.62f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathAlpha"
    )

    val bg = MaterialTheme.colorScheme.background
    val isLiquid = bg == LiquidGlassBackground
    val isDark = bg.red < 0.2f && !isLiquid

    // Calculate viscous liquid deformation factors
    val bounceVal = liquidBounce.value
    val deformX = if (isLiquid) 1f + 0.045f * sin(bounceVal * PI).toFloat() else 1f
    val deformY = if (isLiquid) 1f - 0.045f * sin(bounceVal * PI).toFloat() else 1f

    // High-Contrast Glassmorphic Text Shadow
    val textShadow = if (isLiquid || isDark) {
        Shadow(color = Color(0x800A192F), offset = Offset(0f, 2f), blurRadius = 4f)
    } else {
        Shadow(color = Color.White.copy(alpha = 0.85f), offset = Offset(0f, 1f), blurRadius = 2f)
    }

    val mantraColor = when {
        isLiquid -> Color(0xFFFFD166) // Radiant Celestial Gold
        isDark -> Color(0xFFFFB685)   // Temple Dark Flame
        else -> SaffronPrimary        // Sacred Deep Saffron
    }

    val counterColor = when {
        isLiquid -> Color(0xFFFFFFFF) // Crisp Pure White
        isDark -> Color(0xFFFFFFFF)   // Crisp Pure White
        else -> Color(0xFF26180E)     // Deep Sandalwood
    }

    val tapHintColor = when {
        isLiquid -> Color(0xFF48CAE4) // Vibrant Liquid Cyan
        isDark -> Color(0xFFFFCA3A)   // Warm Amber Gold
        else -> SaffronPrimary.copy(alpha = 0.85f)
    }

    Box(
        modifier = modifier
            .size(200.dp)
            .graphicsLayer {
                scaleX = pressScale * deformX
                scaleY = pressScale * deformY
            },
        contentAlignment = Alignment.Center
    ) {
        // Expanding Liquid Droplet Wavefront Ring (Wave on tap)
        if (rippleProgress.value > 0f && rippleProgress.value < 1f) {
            val progress = rippleProgress.value
            val waveAlpha = (1f - progress).coerceIn(0f, 1f) * 0.80f
            val waveScale = 1.0f + progress * 0.75f
            val waveColor = if (isLiquid) Color(0xFF48CAE4) else SacredGoldGlow

            Canvas(
                modifier = Modifier
                    .size(200.dp)
                    .graphicsLayer {
                        scaleX = waveScale
                        scaleY = waveScale
                    }
            ) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            waveColor.copy(alpha = 0f),
                            waveColor.copy(alpha = waveAlpha),
                            waveColor.copy(alpha = 0f)
                        )
                    ),
                    radius = size.minDimension / 2f,
                    style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }

        // Outer Breathing Halo Aura (Hardware-accelerated)
        val outerAuraEnd = if (isLiquid) Color(0xFFC77DFF).copy(alpha = 0f) else SacredAmberRadiance.copy(alpha = 0f)
        Box(
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    scaleX = breathScale
                    scaleY = breathScale
                    alpha = breathAlpha
                }
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = if (isLiquid) {
                            listOf(
                                Color(0xFF00B4D8).copy(alpha = 0.50f),
                                Color(0xFFC77DFF).copy(alpha = 0.25f),
                                outerAuraEnd
                            )
                        } else {
                            listOf(
                                SacredGoldGlow.copy(alpha = 0.55f),
                                SacredAmberRadiance.copy(alpha = 0.25f),
                                outerAuraEnd
                            )
                        }
                    )
                )
        )

        // Middle Specular Liquid Aura Ring
        val middleAuraEnd = if (isLiquid) Color(0xFF48CAE4).copy(alpha = 0f) else SacredGoldGlow.copy(alpha = 0f)
        Box(
            modifier = Modifier
                .size(172.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = if (isLiquid) {
                            listOf(
                                Color(0xFFFFD166).copy(alpha = 0.35f),
                                Color(0xFF48CAE4).copy(alpha = 0.15f),
                                middleAuraEnd
                            )
                        } else {
                            listOf(
                                SacredGoldGlow.copy(alpha = 0.35f),
                                middleAuraEnd
                            )
                        }
                    )
                )
        )

        // Main Frosted Liquid Glass Jap Surface
        Box(
            modifier = Modifier
                .size(154.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = when {
                            isLiquid -> listOf(
                                Color(0xE81A365D), // Deep Crystal Sapphire Meniscus Core
                                Color(0xF2122846),
                                Color(0xF80E1F36)
                            )
                            isDark -> listOf(
                                Color(0xE64A2D1A),
                                Color(0xD9331E12),
                                Color(0xCC20130B)
                            )
                            else -> listOf(
                                Color(0xF2FFFFFF),
                                Color(0xE6FFF6EA),
                                Color(0xD9F7E5CF)
                            )
                        }
                    )
                )
                .border(
                    width = if (isLiquid) 2.2.dp else 1.5.dp,
                    brush = when {
                        isLiquid -> Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.98f),
                                Color(0xEE48CAE4),
                                Color(0xAAC77DFF),
                                Color(0xEEFFD166),
                                Color.White.copy(alpha = 0.50f)
                            )
                        )
                        isDark -> Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.35f),
                                SacredGoldGlow.copy(alpha = 0.35f),
                                Color.White.copy(alpha = 0.08f)
                            )
                        )
                        else -> Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.90f),
                                Color.White.copy(alpha = 0.40f),
                                Color(0xFFFFD54F).copy(alpha = 0.40f)
                            )
                        )
                    },
                    shape = CircleShape
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        // Trigger liquid spring bounce + droplet ripple in parallel
                        coroutineScope.launch {
                            liquidBounce.snapTo(0f)
                            liquidBounce.animateTo(
                                targetValue = 1f,
                                animationSpec = spring(
                                    dampingRatio = 0.58f,
                                    stiffness = 380f
                                )
                            )
                        }
                        coroutineScope.launch {
                            rippleProgress.snapTo(0f)
                            rippleProgress.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(durationMillis = 480, easing = FastOutSlowInEasing)
                            )
                        }
                        onTap()
                    }
                )
                .testTag("jap_main_button"),
            contentAlignment = Alignment.Center
        ) {
            // Ultra-Glossy Convex Lens Top-Edge Highlight (Thin crescent rim so it does NOT wash text!)
            if (isLiquid) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.70f)
                        .height(26.dp)
                        .align(Alignment.TopCenter)
                        .clip(CircleShape)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.50f),
                                    Color.White.copy(alpha = 0.12f),
                                    Color.White.copy(alpha = 0f)
                                )
                            )
                        )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Sacred Mantra (Devanagari) - Crisp, Radiant & Shadowed
                Text(
                    text = mantraText.split("\n").firstOrNull() ?: "श्री राधा",
                    style = TextStyle(
                        fontSize = if (mantraText.length > 8) 22.sp else 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = mantraColor,
                        shadow = textShadow,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Current Bead / 108 Count - Smooth animated counter with crystal pill styling
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            if (isLiquid) Color(0x3500B4D8)
                            else Color.Transparent
                        )
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                ) {
                    AnimatedContent(
                        targetState = currentBead,
                        transitionSpec = {
                            (slideInVertically(
                                animationSpec = spring(dampingRatio = 0.72f, stiffness = 380f),
                                initialOffsetY = { -it / 2 }
                            ) + fadeIn(animationSpec = tween(160))) togetherWith
                            (slideOutVertically(
                                animationSpec = spring(dampingRatio = 0.72f, stiffness = 380f),
                                targetOffsetY = { it / 2 }
                            ) + fadeOut(animationSpec = tween(130)))
                        },
                        label = "beadCountTransition"
                    ) { bead ->
                        Text(
                            text = "${bead + 1} / 108",
                            style = TextStyle(
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = counterColor,
                                shadow = textShadow,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                // Tap Prompt
                Text(
                    text = tapPrompt,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = tapHintColor,
                        shadow = textShadow,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}
