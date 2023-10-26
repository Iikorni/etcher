package io.iikorni.etcher.datagen

import io.iikorni.etcher.init.EtcherBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider

class EtcherLootTableGenerator(dataOutput: FabricDataOutput?) : FabricBlockLootTableProvider(dataOutput) {
    override fun generate() {
        addDrop(EtcherBlocks.ETCHER_BLOCK, drops(EtcherBlocks.ETCHER_BLOCK))
    }
}