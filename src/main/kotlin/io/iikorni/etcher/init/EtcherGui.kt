package io.iikorni.etcher.init

import io.iikorni.etcher.Etcher
import io.iikorni.etcher.screen.EtcherScreenHandler
import net.minecraft.registry.Registry
import net.minecraft.resource.featuretoggle.FeatureFlags
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier

object EtcherGui {
    var ETCHER_SCREEN_HANDLER =
        ScreenHandlerType(::EtcherScreenHandler, FeatureFlags.VANILLA_FEATURES)

    fun initScreenHandlers(registry: Registry<ScreenHandlerType<*>>) {
        Registry.register(registry, Identifier(Etcher.MOD_ID, "etcher"), ETCHER_SCREEN_HANDLER)
    }
}