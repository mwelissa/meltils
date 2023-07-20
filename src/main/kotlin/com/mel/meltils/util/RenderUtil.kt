package com.mel.meltils.util

import net.minecraft.client.Minecraft
import kotlin.math.*

object RenderUtil {
    private val mc = Minecraft.getMinecraft()


    fun getYawTo(x: Double, z: Double): Double {
        val x = x - mc.thePlayer.posX
        val z = z - mc.thePlayer.posZ
        val sign = if (x >= 0) 1 else -1
        var extra = (floor(abs(mc.thePlayer.rotationYaw / 360)) * if (mc.thePlayer.rotationYaw >= 0) 1 else -1 * 360).toDouble()
        if (z > 0) {
            extra += -Math.toDegrees(atan(x / z)) % 360
        } else if (z == 0.0) {
            extra += -90 * sign % 360
        } else {
            extra += -180 * sign - Math.toDegrees(atan(x / z)) % 360
        }
        return extra
    }


    fun getPitchTo(x0: Double, y0: Double, z0: Double): Double {
        val x = y0 - mc.thePlayer.posY - mc.thePlayer.eyeHeight
        val z = sqrt((x0 - mc.thePlayer.posX).pow(2) + (z0 - mc.thePlayer.posZ).pow(2))
        val sign = if (x >= 0) 1.0 else -1.0
        return if (z == 0.0) {
            -90 * sign
        } else {
            (-Math.toDegrees(atan(x / z)) % 360).coerceIn(-90.0, 90.0)
        }
    }
}