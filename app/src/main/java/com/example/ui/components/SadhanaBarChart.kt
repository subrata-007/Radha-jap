package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.DailySadhana
import com.example.localization.AppLanguage
import com.example.localization.LanguageProvider
import com.example.ui.theme.SacredAmberRadiance
import com.example.ui.theme.SacredGoldGlow
import com.example.ui.theme.TulsiTertiary
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun SadhanaBarChart(
    sadhanaList: List<DailySadhana>,
    targetMalas: Int,
    language: AppLanguage = AppLanguage.HINDI,
    modifier: Modifier = Modifier
) {
    // Generate the last 7 days keys
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dayFormat = SimpleDateFormat("E", Locale.getDefault())

    val calendar = Calendar.getInstance()
    val last7Days = (6 downTo 0).map { daysAgo ->
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_YEAR, -daysAgo)
        val dateStr = sdf.format(c.time)
        val dayLabel = dayFormat.format(c.time)
        val matchingItem = sadhanaList.find { it.dateString == dateStr }
        val malas = matchingItem?.totalMalas ?: 0
        Triple(dateStr, dayLabel, malas)
    }

    val maxMalas = (last7Days.maxOfOrNull { it.third } ?: 0).coerceAtLeast(targetMalas).coerceAtLeast(10)

    val targetLineColor = Color.White.copy(alpha = 0.45f)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0x2EFFFFFF),
                        Color(0x18182D48)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(Color.White.copy(alpha = 0.85f), Color(0x6648CAE4), Color.White.copy(alpha = 0.25f))
                ),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = LanguageProvider.getHistoryChartTitle(language),
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = LanguageProvider.getTargetGoalLabel(language, targetMalas),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Chart Canvas
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        ) {
            val width = size.width
            val height = size.height
            val numBars = last7Days.size
            val barWidth = (width / numBars) * 0.45f
            val slotWidth = width / numBars

            // Draw Target Dotted/Light Line
            val targetY = height - ((targetMalas.toFloat() / maxMalas) * height * 0.85f)
            drawLine(
                color = targetLineColor,
                start = Offset(0f, targetY),
                end = Offset(width, targetY),
                strokeWidth = 1.5.dp.toPx()
            )

            last7Days.forEachIndexed { index, item ->
                val malas = item.third
                val barHeight = ((malas.toFloat() / maxMalas) * height * 0.85f).coerceAtLeast(4.dp.toPx())
                val x = (index * slotWidth) + (slotWidth - barWidth) / 2f
                val y = height - barHeight

                val isMet = malas >= targetMalas && malas > 0
                val barBrush = if (isMet) {
                    Brush.verticalGradient(listOf(Color(0xFF80ED99), Color(0xFF2E633B)))
                } else {
                    Brush.verticalGradient(listOf(Color(0xFFFFD166), Color(0xFFC05809)))
                }

                drawRoundRect(
                    brush = barBrush,
                    topLeft = Offset(x, y),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Labels row under bars
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            last7Days.forEach { (_, label, malas) ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$malas",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = label.take(3),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}
