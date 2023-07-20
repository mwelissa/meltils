package com.mel.meltils

import com.mel.meltils.commands.MeltilsCommand
import com.mel.meltils.core.Config
import com.mel.meltils.events.packet.PacketEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

@Mod(
    modid = Meltils.MODID,
    version = Meltils.VERSION,
    name = Meltils.NAME,
    clientSideOnly = true,
    acceptedMinecraftVersions = "[1.8.9]",
    modLanguageAdapter = "gg.essential.api.utils.KotlinAdapter"
)
object Meltils: CoroutineScope {
    const val NAME = "Meltils"
    const val MODID = "meltils"
    const val VERSION = "1.0"

    override val coroutineContext: CoroutineContext = Executors.newFixedThreadPool(10).asCoroutineDispatcher() + SupervisorJob()

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        Config.preload()
        MeltilsCommand.register()
        MinecraftForge.EVENT_BUS.register(PacketEvent)
    }
}