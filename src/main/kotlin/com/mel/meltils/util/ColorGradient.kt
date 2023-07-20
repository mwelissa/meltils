package com.mel.meltils.util

import java.awt.Color
import kotlin.math.floor

class ColorGradient(var colors: MutableList<Color>, var timePerColor: Float = 1000F) {

    private val initialized = System.currentTimeMillis()

    fun get(offset: Float = 0F): Color {
        if (colors.isEmpty()) return Color.WHITE
        if (colors.size == 1) return colors.first()
        val elapsed = (System.currentTimeMillis() + offset.toLong() - initialized) % (timePerColor * colors.size)
        val i = floor((elapsed / timePerColor).toDouble()).toInt()
        val c1 = colors[i]
        val c2 = if (c1 == colors.last()) {
            colors.first()
        } else {
            colors[i + 1]
        }
        if (c1 == c2) {
            return c1
        }
        val progress = elapsed / timePerColor - floor(elapsed / timePerColor)
        val red = c1.red * (1 - progress) + c2.red * progress
        val green = c1.green * (1 - progress) + c2.green * progress
        val blue = c1.blue * (1 - progress) + c2.blue * progress
        return try {
            Color(red.toInt(), green.toInt(), blue.toInt())
        } catch (e: IllegalArgumentException) {
            Color.WHITE
        }
    }
}