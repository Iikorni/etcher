package io.iikorni.etcher.datagen

import io.iikorni.etcher.Etcher
import io.iikorni.etcher.init.EtcherBlocks
import io.iikorni.etcher.init.EtcherItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.*
import net.minecraft.util.Identifier

class EtcherModelGenerator(generator: FabricDataOutput) : FabricModelProvider(generator) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        blockStateModelGenerator.registerSingleton(EtcherBlocks.ETCHER_BLOCK, TexturedModel.makeFactory(
            { TextureMap()
                .put(TextureKey.SIDE, Identifier("minecraft", "block/jukebox_side"))
                .put(TextureKey.TOP, Identifier(Etcher.MOD_ID, "block/etcher_top")) },
            Models.CUBE_TOP))
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        itemModelGenerator.register(EtcherItems.BLANK_DISC, Models.GENERATED);
    }

}