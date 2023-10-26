package io.iikorni.etcher.init

import io.iikorni.etcher.Etcher
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object EtcherItemGroups {
    private val MAIN_GROUP: ItemGroup = FabricItemGroup.builder()
        .icon { ItemStack(EtcherItems.BLANK_DISC) }
        .displayName(Text.translatable("itemGroup.${Etcher.MOD_ID}.main_group"))
        .entries(EtcherItems::registerMainGroup)
        .build()

    fun init(registry: Registry<ItemGroup>) {
        Registry.register(registry, Identifier(Etcher.MOD_ID, "main_group"), MAIN_GROUP)
    }
}