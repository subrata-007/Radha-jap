package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.GlassBorderDark
import com.example.ui.theme.GlassBorderLight
import com.example.ui.theme.GlassCardBackgroundDark
import com.example.ui.theme.GlassCardBackgroundLight
import com.example.ui.theme.LiquidGlassBackground
import com.example.ui.theme.SacredAmberRadiance
import com.example.ui.theme.SacredGoldGlow
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Ultra-Glossy Liquid Material Glass Card
 * Featuring 3D convex specular curved meniscus, prismatic refraction edges,
 * colored caustic transmission shadows, and deep frosted optical thickness.
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(22.dp),
    elevation: Dp = 8.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val bgColorScheme = MaterialTheme.colorScheme.background
    val isLiquid = bgColorScheme == LiquidGlassBackground
    val isDark = bgColorScheme.red < 0.2f && !isLiquid

    val bgBrush = when {
        isLiquid -> Brush.verticalGradient(
            colors = listOf(
                Color(0x2EFFFFFF), // Frosted milky white specular top reflection
                Color(0x1A1B2D48), // Deep crystal sapphire translucency
                Color(0x2614243A)  // Ambient bottom bounce
            )
        )
        isDark -> Brush.verticalGradient(
            colors = listOf(
                Color(0xD92E1C12),
                Color(0xB31F130B)
            )
        )
        else -> Brush.verticalGradient(
            colors = listOf(
                Color(0xE6FFFDF8),
                Color(0xCCF7EFE2)
            )
        )
    }

    val borderBrush = when {
        isLiquid -> Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.90f),   // Top-left pinpoint specular reflection
                Color(0xDD48CAE4),                 // Yamuna crystal cyan refraction
                Color(0x99C77DFF),                 // Soft lotus amethyst dispersion
                Color(0xCCFFD166),                 // Celestial gold light fringe
                Color.White.copy(alpha = 0.30f)    // Ambient rim
            ),
            start = Offset(0f, 0f),
            end = Offset(450f, 650f)
        )
        isDark -> Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.35f),
                GlassBorderDark,
                Color.White.copy(alpha = 0.08f)
            )
        )
        else -> Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.95f),
                GlassBorderLight,
                Color.White.copy(alpha = 0.35f)
            )
        )
    }

    val cardModifier = if (onClick != null) {
        modifier.liquidClickable(onClick = onClick)
    } else {
        modifier
    }

    Box(
        modifier = cardModifier
            .clip(shape)
            .background(bgBrush)
            .border(
                width = if (isLiquid) 1.5.dp else 1.dp,
                brush = borderBrush,
                shape = shape
            )
    ) {
        content()
    }
}

/**
 * Liquid Interactive Touch Modifier
 * Adds viscous surface-tension deformation, gelatinous spring bounce,
 * concentric liquid droplet wavefront ripple, and specular glint.
 */
@Composable
fun Modifier.liquidClickable(
    enabled: Boolean = true,
    rippleColor: Color? = null,
    onClick: () -> Unit
): Modifier {
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val liquidBounce = remember { Animatable(0f) }
    val liquidRipple = remember { Animatable(0f) }
    val liquidSheen = remember { Animatable(0f) }

    val isLiquid = MaterialTheme.colorScheme.background == LiquidGlassBackground

    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "liquidPress"
    )

    val bounceVal = liquidBounce.value
    val deformX = 1f + 0.045f * sin(bounceVal * PI).toFloat()
    val deformY = 1f - 0.045f * sin(bounceVal * PI).toFloat()

    val resolvedRippleColor = rippleColor ?: if (isLiquid) Color(0xFF48CAE4) else SacredGoldGlow

    return this
        .graphicsLayer {
            scaleX = pressScale * deformX
            scaleY = pressScale * deformY
        }
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            enabled = enabled,
            onClick = {
                coroutineScope.launch {
                    liquidBounce.snapTo(0f)
                    liquidBounce.animateTo(
                        targetValue = 1f,
                        animationSpec = spring(
                            dampingRatio = 0.62f,
                            stiffness = 380f
                        )
                    )
                }
                coroutineScope.launch {
                    liquidRipple.snapTo(0f)
                    liquidRipple.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = 480, easing = FastOutSlowInEasing)
                    )
                }
                coroutineScope.launch {
                    liquidSheen.snapTo(0f)
                    liquidSheen.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = 420, easing = FastOutSlowInEasing)
                    )
                }
                onClick()
            }
        )
        .drawWithContent {
            drawContent()

            // Dynamic expanding concentric liquid droplet wavefront
            if (liquidRipple.value > 0f && liquidRipple.value < 1f) {
                val progress = liquidRipple.value
                val waveAlpha = (1f - progress).coerceIn(0f, 1f) * 0.70f
                val maxRadius = (size.maxDimension / 2f) * (0.25f + 1.25f * progress)
                val center = Offset(size.width / 2f, size.height / 2f)

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            resolvedRippleColor.copy(alpha = 0f),
                            resolvedRippleColor.copy(alpha = waveAlpha),
                            Color(0xFFC77DFF).copy(alpha = waveAlpha * 0.5f),
                            resolvedRippleColor.copy(alpha = 0f)
                        ),
                        center = center,
                        radius = maxRadius
                    ),
                    center = center,
                    radius = maxRadius,
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )
            }

            // Specular liquid flash sweep across surface on tap
            if (liquidSheen.value > 0f && liquidSheen.value < 1f) {
                val sheenProg = liquidSheen.value
                val sheenX = size.width * (sheenProg * 1.5f - 0.25f)
                val sheenAlpha = (1f - (sheenProg - 0.5f) * (sheenProg - 0.5f) * 4f).coerceIn(0f, 1f) * 0.45f

                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0f),
                            Color.White.copy(alpha = sheenAlpha),
                            Color.White.copy(alpha = 0f)
                        ),
                        start = Offset(sheenX - 40f, 0f),
                        end = Offset(sheenX + 40f, size.height)
                    ),
                    start = Offset(sheenX, 0f),
                    end = Offset(sheenX, size.height),
                    strokeWidth = 35.dp.toPx()
                )
            }
        }
}

/**
 * Liquid Glass Chip
 * High-contrast, crystal pill chip with 3D liquid deformation, ripple wavefront, and luminous jewel finish.
 */
@Composable
fun LiquidGlassChip(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    label: @Composable () -> Unit
) {
    val isLiquid = MaterialTheme.colorScheme.background == LiquidGlassBackground
    val isDark = MaterialTheme.colorScheme.background.red < 0.2f && !isLiquid

    val containerBrush = when {
        selected -> when {
            isLiquid -> Brush.linearGradient(
                listOf(Color(0xFF0077B6), Color(0xFF00B4D8), Color(0xFFFFD166))
            )
            isDark -> Brush.linearGradient(
                listOf(Color(0xFFE65100), Color(0xFFFF8F00), Color(0xFFFFB300))
            )
            else -> Brush.linearGradient(
                listOf(Color(0xFFD84315), Color(0xFFF57C00), Color(0xFFFFB300))
            )
        }
        else -> when {
            isLiquid -> Brush.verticalGradient(
                listOf(Color(0x28FFFFFF), Color(0x1218283E))
            )
            isDark -> Brush.verticalGradient(
                listOf(Color(0x2EFFFFFF), Color(0x14FFFFFF), Color(0x161A1009))
            )
            else -> Brush.verticalGradient(
                listOf(Color(0xF8FFF9F0), Color(0xEEF5E6D3))
            )
        }
    }

    val borderBrush = when {
        selected -> when {
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
        isDark -> Brush.verticalGradient(
            listOf(Color.White.copy(alpha = 0.22f), Color.White.copy(alpha = 0.08f))
        )
        else -> Brush.verticalGradient(
            listOf(Color.White.copy(alpha = 0.90f), Color(0x30C05809))
        )
    }

    val shape = RoundedCornerShape(16.dp)

    Box(
        modifier = modifier
            .shadow(
                elevation = if (selected) 4.dp else 0.dp,
                shape = shape,
                spotColor = SacredAmberRadiance.copy(alpha = 0.40f)
            )
            .liquidClickable(
                rippleColor = if (isLiquid) Color(0xFFFFD166) else SacredAmberRadiance,
                onClick = onClick
            )
            .clip(shape)
            .background(containerBrush)
            .border(
                width = 1.dp,
                brush = borderBrush,
                shape = shape
            )
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            LocalContentColor provides when {
                selected -> Color.White
                isDark || isLiquid -> Color.White.copy(alpha = 0.88f)
                else -> MaterialTheme.colorScheme.onSurface
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (icon != null) {
                    icon()
                    Box(modifier = Modifier.size(6.dp))
                }
                label()
            }
        }
    }
}

/**
 * Liquid Glass Button
 * A full interactive button with gelatinous bounce, water ripple wavefront, and specular highlight.
 */
@Composable
fun LiquidGlassButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    primary: Boolean = true,
    shape: Shape = RoundedCornerShape(18.dp),
    content: @Composable RowScope.() -> Unit
) {
    val isLiquid = MaterialTheme.colorScheme.background == LiquidGlassBackground
    val isDark = MaterialTheme.colorScheme.background.red < 0.2f && !isLiquid

    val bgBrush = when {
        primary -> when {
            isLiquid -> Brush.linearGradient(
                listOf(Color(0xFF0077B6), Color(0xFF00B4D8), Color(0xFFFFD166))
            )
            isDark -> Brush.linearGradient(
                listOf(Color(0xFFD84315), Color(0xFFFF8F00), Color(0xFFFFB300))
            )
            else -> Brush.linearGradient(
                listOf(Color(0xFFD84315), Color(0xFFE26D14), Color(0xFFFFB300))
            )
        }
        else -> Brush.verticalGradient(
            if (isLiquid) listOf(Color(0x3348CAE4), Color(0x1A0077B6))
            else if (isDark) listOf(Color(0x35FFFFFF), Color(0x1A25150C))
            else listOf(Color(0xF0FFF8EE), Color(0xD8F5E6D3))
        )
    }

    val borderBrush = when {
        primary -> Brush.verticalGradient(
            listOf(
                Color.White.copy(alpha = 0.80f),
                Color(0xFFFFD54F).copy(alpha = 0.50f),
                Color(0x20FFA000)
            )
        )
        isLiquid -> Brush.linearGradient(
            listOf(Color.White.copy(alpha = 0.60f), Color(0x4048CAE4), Color.Transparent)
        )
        isDark -> Brush.verticalGradient(
            listOf(Color.White.copy(alpha = 0.30f), Color.White.copy(alpha = 0.08f))
        )
        else -> Brush.verticalGradient(
            listOf(Color.White.copy(alpha = 0.85f), Color(0x30C05809))
        )
    }

    Box(
        modifier = modifier
            .shadow(
                elevation = if (primary) 5.dp else 1.dp,
                shape = shape,
                spotColor = SacredAmberRadiance.copy(alpha = 0.45f)
            )
            .liquidClickable(
                enabled = enabled,
                rippleColor = if (isLiquid) Color(0xFFFFD166) else SacredAmberRadiance,
                onClick = onClick
            )
            .clip(shape)
            .background(bgBrush)
            .border(
                width = 1.2.dp,
                brush = borderBrush,
                shape = shape
            )
            .padding(horizontal = 20.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            LocalContentColor provides when {
                primary -> Color.White
                isDark || isLiquid -> Color.White.copy(alpha = 0.90f)
                else -> MaterialTheme.colorScheme.onSurface
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                content = content
            )
        }
    }
}

/**
 * Liquid Glass Icon Button
 * A dedicated liquid crystal sphere button with viscous deformation, specular sheen, and ripple
 */
@Composable
fun LiquidGlassIconButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    tint: Color = MaterialTheme.colorScheme.primary,
    enabled: Boolean = true,
    activeGlow: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val bounce = remember { Animatable(0f) }
    val ripple = remember { Animatable(0f) }

    val isLiquid = MaterialTheme.colorScheme.background == LiquidGlassBackground
    val isDark = MaterialTheme.colorScheme.background.red < 0.2f && !isLiquid

    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
        label = "btnPress"
    )

    val deformX = 1f + 0.08f * sin(bounce.value * PI).toFloat()
    val deformY = 1f - 0.08f * sin(bounce.value * PI).toFloat()

    Box(
        modifier = modifier
            .size(size)
            .shadow(
                elevation = if (activeGlow) 4.dp else 2.dp,
                shape = CircleShape,
                spotColor = SacredAmberRadiance.copy(alpha = 0.35f)
            )
            .graphicsLayer {
                scaleX = pressScale * deformX
                scaleY = pressScale * deformY
            },
        contentAlignment = Alignment.Center
    ) {
        // Liquid Expanding Wave on click
        if (ripple.value > 0f && ripple.value < 1f) {
            val progress = ripple.value
            val waveAlpha = (1f - progress).coerceIn(0f, 1f) * 0.85f
            val waveScale = 1.0f + progress * 0.75f
            val waveColor = if (isLiquid) Color(0xFF48CAE4) else SacredGoldGlow

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
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
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }

        val bgBrush = when {
            activeGlow -> Brush.linearGradient(
                listOf(Color(0xFFD84315), Color(0xFFFF8F00), Color(0xFFFFD54F))
            )
            isLiquid -> Brush.linearGradient(
                listOf(Color(0x3EFFFFFF), Color(0x240077B6), Color(0x1800365A))
            )
            isDark -> Brush.linearGradient(
                listOf(Color(0x38FFFFFF), Color(0x224A2814), Color(0x2A1F1209))
            )
            else -> Brush.linearGradient(
                listOf(Color(0xF5FFFFFF), Color(0xE4F5E6D3))
            )
        }

        val borderBrush = when {
            activeGlow -> Brush.linearGradient(
                listOf(Color.White, Color(0xFFFFD166), Color.White.copy(alpha = 0.85f))
            )
            isLiquid -> Brush.linearGradient(
                listOf(Color.White.copy(alpha = 0.90f), Color(0xCC48CAE4), Color(0x99C77DFF), Color.White.copy(alpha = 0.35f))
            )
            isDark -> Brush.linearGradient(
                listOf(Color.White.copy(alpha = 0.80f), Color(0xFFFFD54F).copy(alpha = 0.65f), Color.White.copy(alpha = 0.20f), Color(0x30FFA000))
            )
            else -> Brush.verticalGradient(
                listOf(Color.White.copy(alpha = 0.95f), Color(0x40C05809))
            )
        }

        // Liquid Glass Capsule Body - Jewel Crystal Sphere
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(bgBrush)
                .border(
                    width = 1.2.dp,
                    brush = borderBrush,
                    shape = CircleShape
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    onClick = {
                        coroutineScope.launch {
                            bounce.snapTo(0f)
                            bounce.animateTo(1f, spring(dampingRatio = 0.65f, stiffness = 380f))
                        }
                        coroutineScope.launch {
                            ripple.snapTo(0f)
                            ripple.animateTo(1f, tween(420, easing = FastOutSlowInEasing))
                        }
                        onClick()
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            val resolvedTint = when {
                activeGlow -> Color.White
                isDark && tint == MaterialTheme.colorScheme.primary -> SacredAmberRadiance
                else -> tint
            }
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = resolvedTint,
                modifier = Modifier.size(size * 0.54f)
            )
        }
    }
}

/**
 * 120 FPS High-Smoothness Fluid Liquid Ambient Backdrop
 * Simulates ethereal deep Yamuna river caustics, flowing celestial auroras,
 * and luminous refraction ribbons. Zero circular black spots or dirty gradient artifacts.
 */
@Composable
fun Modifier.fluidAmbientBackdrop(themeMode: String): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "liquidBackdrop")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 22000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    val isLiquid = themeMode == "liquid_glass"
    val isDark = themeMode == "dark"

    return this.drawBehind {
        val width = size.width
        val height = size.height

        val baseBrush = when {
            isLiquid -> Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF0B1B32),
                    Color(0xFF112644),
                    Color(0xFF19375F),
                    Color(0xFF122846),
                    Color(0xFF0B1B32)
                )
            )
            isDark -> Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF1A1009),
                    Color(0xFF28180E),
                    Color(0xFF331E12),
                    Color(0xFF28180E),
                    Color(0xFF1A1009)
                )
            )
            else -> Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFFFDF8),
                    Color(0xFFFAF2E4),
                    Color(0xFFFFF8ED),
                    Color(0xFFFAF2E4),
                    Color(0xFFFFFDF8)
                )
            )
        }
        drawRect(brush = baseBrush)

        // Seamless, full-screen flowing aurora sheets providing radiant backlight for frosted glass
        if (isLiquid) {
            // 1. Diagonal Yamuna Cyan & Azure divine water aurora (smooth linear gradient sweep, NO circular spots)
            val cyanOffset1 = 0.25f + 0.12f * sin(phase * 0.8f)
            val cyanOffset2 = 0.75f + 0.12f * cos(phase * 0.8f)
            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0x3548CAE4),
                        Color(0x1F0077B6),
                        Color(0x080077B6),
                        Color(0x000077B6)
                    ),
                    start = Offset(width * cyanOffset1, 0f),
                    end = Offset(width * cyanOffset2, height * 0.65f)
                )
            )

            // 2. Sacred Lotus Amethyst aurora sweep
            val lotusOffset = 0.5f + 0.15f * cos(phase * 0.6f)
            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0x00C77DFF),
                        Color(0x22C77DFF),
                        Color(0x157B2CBF),
                        Color(0x007B2CBF)
                    ),
                    start = Offset(0f, height * lotusOffset),
                    end = Offset(width, height * (lotusOffset + 0.45f))
                )
            )

            // 3. Radha Golden Amber celestial radiance aurora sweep
            val goldOffset = 0.65f + 0.10f * sin(phase * 1.0f)
            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0x00FFD166),
                        Color(0x18FFD166),
                        Color(0x28FFB703),
                        Color(0x05FFB703)
                    ),
                    start = Offset(width * 0.1f, height * goldOffset),
                    end = Offset(width * 0.95f, height)
                )
            )
        }

        // Subtle ethereal caustic wave ribbons
        if (isLiquid) {
            val wave1 = (sin(phase * 1.2f) * 25f)
            val path1 = Path().apply {
                moveTo(0f, height * 0.35f + wave1)
                cubicTo(
                    width * 0.35f, height * 0.33f - wave1,
                    width * 0.65f, height * 0.38f + wave1,
                    width, height * 0.35f - wave1
                )
            }
            drawPath(
                path = path1,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0x0048CAE4),
                        Color(0x5548CAE4),
                        Color(0x55FFD166),
                        Color(0x0048CAE4)
                    )
                ),
                style = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round)
            )

            val wave2 = (cos(phase * 0.9f) * 22f)
            val path2 = Path().apply {
                moveTo(0f, height * 0.65f + wave2)
                cubicTo(
                    width * 0.30f, height * 0.68f - wave2,
                    width * 0.70f, height * 0.62f + wave2,
                    width, height * 0.66f - wave2
                )
            }
            drawPath(
                path = path2,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0x00C77DFF),
                        Color(0x45C77DFF),
                        Color(0x4548CAE4),
                        Color(0x00C77DFF)
                    )
                ),
                style = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}
