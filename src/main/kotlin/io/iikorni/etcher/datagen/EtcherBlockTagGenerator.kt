package io.iikorni.etcher.datagen

import io.iikorni.etcher.init.EtcherBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.BlockTags
import java.util.concurrent.CompletableFuture

class EtcherBlockTagGenerator(output: FabricDataOutput,
                              registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricTagProvider.BlockTagProvider(output, registriesFuture) {
    override fun configure(arg: RegistryWrapper.WrapperLookup) {
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
            .add(EtcherBlocks.ETCHER_BLOCK)
    }
}