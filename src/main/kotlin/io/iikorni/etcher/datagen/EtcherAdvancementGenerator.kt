package io.iikorni.etcher.datagen

import io.iikorni.etcher.Etcher
import io.iikorni.etcher.init.EtcherBlocks
import io.iikorni.etcher.init.EtcherItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider
import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.advancement.AdvancementFrame
import net.minecraft.advancement.criterion.InventoryChangedCriterion
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.ItemTags
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class EtcherAdvancementGenerator(output: FabricDataOutput?,
                                 registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>?
) : FabricAdvancementProvider(output, registryLookup) {
    override fun generateAdvancement(
        registryLookup: RegistryWrapper.WrapperLookup?,
        consumer: Consumer<AdvancementEntry>?
    ) {
        val root = Advancement.Builder.create()
            .display(
                EtcherBlocks.ETCHER_BLOCK,
                Text.translatable("advancements.${Etcher.MOD_ID}.root.title"),
                Text.translatable("advancements.${Etcher.MOD_ID}.root.description"),
                Identifier("textures/block/jukebox_side.png"),
                AdvancementFrame.TASK,
                true,
                true,
                false
            )
            .criterion("got_music_disc", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(
                ItemTags.MUSIC_DISCS).build()))
            .build(consumer, "${Etcher.MOD_ID}/root")

        val blank_disc = Advancement.Builder.create().parent(root)
            .display(
                EtcherItems.BLANK_DISC,
                Text.translatable("advancements.${Etcher.MOD_ID}.blank_disc.title"),
                Text.translatable("advancements.${Etcher.MOD_ID}.blank_disc.description"),
                null,
                AdvancementFrame.TASK,
                true,
                true,
                false
            )
            .criterion("got_blank_disc", InventoryChangedCriterion.Conditions.items(EtcherItems.BLANK_DISC))
            .build(consumer, "${Etcher.MOD_ID}/blank_disc")
    }

}