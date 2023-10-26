package io.iikorni.etcher.screen

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot

class EtcherResultSlot(inventory: Inventory, val canTake: (PlayerEntity) -> Boolean, val onTake: (PlayerEntity, ItemStack) -> Unit, index: Int, x: Int, y: Int) : Slot(inventory, index, x, y) {
    override fun canInsert(stack: ItemStack): Boolean = false

    override fun canTakeItems(playerEntity: PlayerEntity): Boolean {
        return canTake(playerEntity)
    }

    override fun onTakeItem(player: PlayerEntity, stack: ItemStack) {
        onTake(player, stack)
    }
}