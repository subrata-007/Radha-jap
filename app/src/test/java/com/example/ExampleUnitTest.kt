package com.example

import com.example.data.local.DailySadhana
import com.example.data.local.MantraSadhana
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testMalaCompletionCalculation() {
        val totalBeads = 108
        val malas = totalBeads / 108
        val currentBead = totalBeads % 108
        assertEquals(1, malas)
        assertEquals(0, currentBead)
    }

    @Test
    fun testPartialMalaCalculation() {
        val totalBeads = 150
        val malas = totalBeads / 108
        val currentBead = totalBeads % 108
        assertEquals(1, malas)
        assertEquals(42, currentBead)
    }

    @Test
    fun testSankalpaCompletion() {
        val targetMalas = 16
        val completedMalas = 16
        val isCompleted = completedMalas >= targetMalas
        assertTrue(isCompleted)
    }

    @Test
    fun testSeparateMantraTrackingCalculation() {
        // Mantra 1: "श्री राधा" with 324 beads (3 full malas)
        val radhaSadhana = MantraSadhana(
            dateString = "2026-10-06",
            mantraName = "श्री राधा",
            totalBeads = 324,
            totalMalas = 3
        )
        assertEquals(3, radhaSadhana.completedMalas)
        assertEquals(0, radhaSadhana.currentBeadInMala)

        // Mantra 2: "हरे कृष्ण महामंत्र" with 150 beads (1 mala + 42 beads)
        val hareKrishnaSadhana = MantraSadhana(
            dateString = "2026-10-06",
            mantraName = "हरे कृष्ण महामंत्र",
            totalBeads = 150,
            totalMalas = 1
        )
        assertEquals(1, hareKrishnaSadhana.completedMalas)
        assertEquals(42, hareKrishnaSadhana.currentBeadInMala)

        // Overall combined daily totals:
        val combinedBeads = radhaSadhana.totalBeads + hareKrishnaSadhana.totalBeads
        val combinedMalas = combinedBeads / 108
        assertEquals(474, combinedBeads)
        assertEquals(4, combinedMalas)
    }
}
