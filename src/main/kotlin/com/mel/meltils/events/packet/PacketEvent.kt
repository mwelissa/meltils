package com.mel.meltils.events.packet

import com.mel.meltils.Meltils
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent

object PacketEvent {

    @SubscribeEvent
    fun onJoinServer(event: FMLNetworkEvent.ClientConnectedToServerEvent) {
        event.manager.channel().pipeline().addAfter(
            "fml:packet_handler",
            "${Meltils.MODID}_packet_handler",
            CustomChannelDuplexHandler()
        )
    }
}