package io.iikorni.etcher.datagen

import io.iikorni.etcher.init.EtcherBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class EtcherLootTableGenerator(dataOutput: FabricDataOutput?, registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>?) : FabricBlockLootTableProvider(dataOutput, registryLookup) {
    override fun generate() {
        addDrop(EtcherBlocks.ETCHER_BLOCK, drops(EtcherBlocks.ETCHER_BLOCK))
    }
}