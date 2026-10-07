package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.DailySadhana
import com.example.data.local.LifetimeMantraSummary
import com.example.data.local.MantraSadhana
import com.example.localization.AppLanguage
import com.example.localization.LanguageProvider
import com.example.ui.components.GlassCard
import com.example.ui.components.LiquidGlassChip
import com.example.ui.components.SadhanaBarChart
import com.example.ui.components.fluidAmbientBackdrop
import com.example.ui.components.liquidClickable
import com.example.ui.theme.SacredAmberRadiance
import com.example.ui.theme.TulsiTertiary

val RADHA_NAAM_QUOTES = listOf(
    "« राधा नाम परम सुखदाई, भज मन मेरे सदा सुखदाई। »",
    "« कोटि कल्प के पाप कटे, मुख निसरै राधा नाम। »",
    "« सकल मनोरथ पूर्ण हों, जपिये श्री राधा नाम। »",
    "« राधा नाम रस पीवै सोई, जेहि पर कृपा किशोरी की होई। »"
)

@Composable
fun SadhanaHistoryScreen(
    sadhanaList: List<DailySadhana>,
    lifetimeBeads: Int,
    lifetimeMalas: Int,
    targetMalas: Int,
    streakDays: Int,
    todayMantraList: List<MantraSadhana> = emptyList(),
    lifetimeMantraBreakdown: List<LifetimeMantraSummary> = emptyList(),
    appLanguage: AppLanguage = AppLanguage.HINDI,
    themeMode: String = "light",
    modifier: Modifier = Modifier
) {
    val strings = remember(appLanguage) {
        LanguageProvider.getStrings(appLanguage)
    }
    val quotes = remember(strings.devotionalQuotes) {
        if (strings.devotionalQuotes.isNotEmpty()) strings.devotionalQuotes else RADHA_NAAM_QUOTES
    }
    var quoteIndex by remember { mutableIntStateOf(0) }
    var mantraTab by remember { mutableIntStateOf(0) } // 0 = Today, 1 = Lifetime

    Box(
        modifier = modifier
            .fillMaxSize()
            .fluidAmbientBackdrop(themeMode)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("sadhana_history_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = strings.tabSadhana,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = strings.todaySadhanaSummary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Lifetime Key Metrics with Fluid Glass & Liquid Touch
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricCard(
                        title = strings.lifetimeTotal,
                        value = "$lifetimeBeads",
                        subtitle = strings.lifetimeBeads,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = strings.lifetimeMalas,
                        value = "$lifetimeMalas",
                        subtitle = "108 ${strings.beads}",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricCard(
                        title = strings.streak,
                        value = "$streakDays ${strings.days}",
                        subtitle = strings.streak,
                        modifier = Modifier.weight(1f)
                    )
                    val completedDays = sadhanaList.count { it.isSankalpaCompleted }
                    MetricCard(
                        title = strings.sankalpaTarget,
                        value = "$completedDays ${strings.days}",
                        subtitle = "${strings.sankalpaTarget} ${strings.days}",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // 7 Days Visual Chart with Liquid Touch
            item {
                Box(modifier = Modifier.liquidClickable { /* Liquid wave on tap */ }) {
                    SadhanaBarChart(
                        sadhanaList = sadhanaList,
                        targetMalas = targetMalas,
                        language = appLanguage
                    )
                }
            }

            // Dedicated Mantra-Wise Chanting Breakdown Card (Records how many times each mantra was chanted separately)
            item {
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
                                Icon(
                                    imageVector = Icons.Default.Spa,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = strings.mantraWiseRecords,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "${strings.totalToday} • ${strings.lifetimeTotal}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Toggle between Today and Lifetime Mantra Breakdown in sleek segmented pill track
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(18.dp))
                                .background(
                                    if (themeMode == "liquid_glass") Color(0x200E1F36)
                                    else if (themeMode == "dark") Color(0x351E130B)
                                    else Color(0x14000000)
                                )
                                .padding(3.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            LiquidGlassChip(
                                selected = mantraTab == 0,
                                onClick = { mantraTab = 0 },
                                label = {
                                    Text(
                                        text = "${strings.totalToday} (${todayMantraList.size})",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = if (mantraTab == 0) FontWeight.Bold else FontWeight.SemiBold,
                                            fontSize = 12.5.sp
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(42.dp)
                                    .testTag("mantra_tab_today")
                            )
                            LiquidGlassChip(
                                selected = mantraTab == 1,
                                onClick = { mantraTab = 1 },
                                label = {
                                    Text(
                                        text = "${strings.lifetimeTotal} (${lifetimeMantraBreakdown.size})",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = if (mantraTab == 1) FontWeight.Bold else FontWeight.SemiBold,
                                            fontSize = 12.5.sp
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(42.dp)
                                    .testTag("mantra_tab_lifetime")
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        AnimatedContent(
                            targetState = mantraTab,
                            transitionSpec = {
                                fadeIn(animationSpec = tween(240, easing = FastOutSlowInEasing)) togetherWith
                                fadeOut(animationSpec = tween(180, easing = FastOutSlowInEasing))
                            },
                            label = "mantraTabSwitchAnim"
                        ) { selectedTab ->
                            if (selectedTab == 0) {
                                // Today's breakdown by Mantra
                                if (todayMantraList.isEmpty()) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.4f))
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "आज अभी तक किसी मंत्र का जप दर्ज नहीं है। जप आरम्भ करें!",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                } else {
                                    val totalTodayBeads = todayMantraList.sumOf { it.totalBeads }.coerceAtLeast(1)
                                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        todayMantraList.forEach { item ->
                                            val percent = (item.totalBeads.toFloat() / totalTodayBeads.toFloat()).coerceIn(0f, 1f)
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(12.dp))
                                                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.35f))
                                                    .padding(12.dp)
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = item.mantraName,
                                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                                        color = MaterialTheme.colorScheme.onSurface,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    Text(
                                                        text = "${item.totalMalas} ${strings.malas} (${item.totalBeads} ${strings.beads})",
                                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.ExtraBold),
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                }

                                                Spacer(modifier = Modifier.height(6.dp))

                                                LinearProgressIndicator(
                                                    progress = { percent },
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(6.dp)
                                                        .clip(RoundedCornerShape(3.dp)),
                                                    color = MaterialTheme.colorScheme.primary,
                                                    trackColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                                    strokeCap = StrokeCap.Round
                                                )

                                                Spacer(modifier = Modifier.height(4.dp))

                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(
                                                        text = LanguageProvider.getTodayContributionLabel(appLanguage, (percent * 100).toInt()),
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = MaterialTheme.colorScheme.secondary
                                                    )
                                                    Text(
                                                        text = LanguageProvider.getActiveMalaLabel(appLanguage, item.totalBeads % 108),
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                // Lifetime breakdown by Mantra
                                if (lifetimeMantraBreakdown.isEmpty()) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.4f))
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "जीवनकाल का कोई मंत्र रिकॉर्ड अभी नहीं मिला।",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                } else {
                                    val totalLifetimeBeads = lifetimeMantraBreakdown.sumOf { it.lifetimeBeads }.coerceAtLeast(1)
                                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        lifetimeMantraBreakdown.forEach { item ->
                                            val percent = (item.lifetimeBeads.toFloat() / totalLifetimeBeads.toFloat()).coerceIn(0f, 1f)
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(12.dp))
                                                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.35f))
                                                    .padding(12.dp)
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = item.mantraName,
                                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                                        color = MaterialTheme.colorScheme.onSurface,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    Text(
                                                        text = "${item.lifetimeMalas} ${strings.malas} (${item.lifetimeBeads} ${strings.beads})",
                                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.ExtraBold),
                                                        color = SacredAmberRadiance
                                                    )
                                                }

                                                Spacer(modifier = Modifier.height(6.dp))

                                                LinearProgressIndicator(
                                                    progress = { percent },
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(6.dp)
                                                        .clip(RoundedCornerShape(3.dp)),
                                                    color = SacredAmberRadiance,
                                                    trackColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                                    strokeCap = StrokeCap.Round
                                                )

                                                Spacer(modifier = Modifier.height(4.dp))

                                                Text(
                                                    text = "${strings.lifetimeTotal}: ${(percent * 100).toInt()}%",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = MaterialTheme.colorScheme.secondary
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

            // Devotional Glory Quote Card with Liquid click to cycle
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        quoteIndex = (quoteIndex + 1) % quotes.size
                    }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = strings.blessing,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = quotes[quoteIndex % quotes.size],
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }

            // Daily History Section Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = strings.tabSadhana,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${sadhanaList.size} ${strings.days}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            if (sadhanaList.isEmpty()) {
                item {
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.EventNote,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = LanguageProvider.getStartFirstJapLabel(appLanguage),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(sadhanaList) { sadhana ->
                    DailySadhanaRowItem(sadhana = sadhana, appLanguage = appLanguage)
                }
            }
        }
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        onClick = { /* Liquid wave on tap */ }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}

@Composable
fun DailySadhanaRowItem(
    sadhana: DailySadhana,
    appLanguage: AppLanguage = AppLanguage.HINDI
) {
    val rowStrings = LanguageProvider.getStrings(appLanguage)
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = 2.dp,
        onClick = { /* Liquid wave feedback */ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = sadhana.dateString,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = LanguageProvider.getTotalBeadsChantedLabel(appLanguage, sadhana.totalBeads),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${sadhana.totalMalas} ${rowStrings.malas}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "${LanguageProvider.getTargetLabel(appLanguage)}: ${sadhana.sankalpaTargetMalas}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                if (sadhana.isSankalpaCompleted) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Completed",
                        tint = TulsiTertiary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.HourglassTop,
                        contentDescription = "In progress",
                        tint = SacredAmberRadiance,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
