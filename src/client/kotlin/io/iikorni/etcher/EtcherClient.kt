package io.iikorni.etcher

import io.iikorni.etcher.init.EtcherGui
import io.iikorni.etcher.screen.EtcherScreen
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.gui.screen.ingame.HandledScreens

object EtcherClient : ClientModInitializer {
	override fun onInitializeClient() {
		HandledScreens.register(EtcherGui.ETCHER_SCREEN_HANDLER, ::EtcherScreen)
	}
}