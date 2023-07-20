package com.mel.meltils.commands

import com.mel.meltils.core.Config
import gg.essential.api.EssentialAPI
import gg.essential.api.commands.Command
import gg.essential.api.commands.DefaultHandler
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText

object MeltilsCommand: Command("ex") {

    @DefaultHandler
    fun handle() {
        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§z Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do §zeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut §zenim ad minim veniam, quis nostrud exercitation ullamco laboris §znisi ut aliquip ex ea commodo consequat. Duis aute irure dolor §zin reprehenderit in voluptate velit esse cillum dolore eu §zfugiat nulla pariatur. Excepteur sint occaecat cupidatat non §zproident, sunt in culpa qui officia deserunt mollit anim id est §zlaborum. §r"))
        //EssentialAPI.getGuiUtil().openScreen(Config.gui())
    }
}