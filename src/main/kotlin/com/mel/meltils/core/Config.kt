package com.mel.meltils.core

import com.mel.meltils.Meltils
import gg.essential.vigilance.Vigilant
import java.io.File

object Config: Vigilant(File("./config/${Meltils.MODID}config.toml")) {

    init {
        initialize()
    }
}