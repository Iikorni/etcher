package io.iikorni.etcher.init

import io.iikorni.etcher.Etcher
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemGroup.DisplayContext
import net.minecraft.item.ItemGroup.Entries
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity

object EtcherItems {
    val BLANK_DISC = Item(FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON))

    fun init(registry: Registry<Item>) {
        Registry.register(registry, Identifier(Etcher.MOD_ID, "blank_disc"), BLANK_DISC);
    }

    fun registerMainGroup(_displayContext: DisplayContext, entries: Entries) {
        entries.add(BLANK_DISC)
    }
}