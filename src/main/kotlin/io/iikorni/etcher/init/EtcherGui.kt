package io.iikorni.etcher.init

import io.iikorni.etcher.Etcher
import io.iikorni.etcher.screen.EtcherScreenHandler
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.util.Identifier

object EtcherGui {
    val ETCHER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(Identifier(Etcher.MOD_ID, "etcher"), ::EtcherScreenHandler)
}