package io.iikorni.etcher.init

import io.iikorni.etcher.Etcher
import io.iikorni.etcher.block.EtcherBlock
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.MapColor
import net.minecraft.block.enums.Instrument
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.registry.Registry
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier

object EtcherBlocks {
    val ETCHER_BLOCK: Block = EtcherBlock(FabricBlockSettings.create()
        .mapColor(MapColor.DIRT_BROWN)
        .instrument(Instrument.BASS)
        .strength(2.0f, 6.0f)
        .sounds(BlockSoundGroup.WOOD))

    fun init(registry: Registry<Block>) {
        Registry.register(registry, Identifier(Etcher.MOD_ID, "etcher"), ETCHER_BLOCK)
    }

    fun initItems(registry: Registry<Item>) {
        Registry.register(registry, Identifier(Etcher.MOD_ID, "etcher"), BlockItem(ETCHER_BLOCK, FabricItemSettings()))
    }

    fun registerMainGroup(_displayContext: ItemGroup.DisplayContext, entries: ItemGroup.Entries) {
        entries.add(ETCHER_BLOCK)
    }
}