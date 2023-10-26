package io.iikorni.etcher.screen

import com.mojang.datafixers.util.Pair
import io.iikorni.etcher.Etcher
import io.iikorni.etcher.init.EtcherGui
import io.iikorni.etcher.init.EtcherItems
import io.iikorni.etcher.recipe.EtchingRecipe
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.CraftingResultInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.PlayerScreenHandler
import net.minecraft.screen.Property
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.slot.Slot
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class EtcherScreenHandler(syncId: Int, playerInventory: PlayerInventory, private val context: ScreenHandlerContext) : ScreenHandler(EtcherGui.ETCHER_SCREEN_HANDLER, syncId) {
    val input = createInputInventory()

    val output = CraftingResultInventory()

    val levelCost: Property = Property.create()

    init {
        addProperty(levelCost)
        addSlot(Slot(input, 0, 40, 17))
        addSlot(object : Slot(input, 1, 40, 53) {
            override fun canInsert(stack: ItemStack): Boolean {
                return stack.isOf(EtcherItems.BLANK_DISC)
            }

            override fun getBackgroundSprite(): Pair<Identifier, Identifier>? {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier(Etcher.MOD_ID, "item/empty_slot_music_disc"))
            }
        })
        addSlot(EtcherResultSlot(output, ::canTakeFromOutput, ::onTakeFromOutput, 2, 116, 35))

        for (i in 0..2) {
            for (j in 0..8) {
                addSlot(Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
            }
        }

        for (i in 0..8) {
            addSlot(Slot(playerInventory, i, 8 + i * 18, 142))
        }
    }

    private fun canTakeFromOutput(player: PlayerEntity): Boolean = player.isCreative || player.experienceLevel >= levelCost.get()

    private fun onTakeFromOutput(player: PlayerEntity, stack: ItemStack) {
        if (!player.abilities.creativeMode) {
            player.addExperienceLevels(-levelCost.get())
        }

        input.removeStack(0, 1)
        input.removeStack(1, 1)

        this.levelCost.set(0)
    }

    private fun createInputInventory() = object : SimpleInventory(2) {
        override fun markDirty() {
            super.markDirty()
            this@EtcherScreenHandler.onContentChanged(this)
        }
    }

    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory, ScreenHandlerContext.EMPTY)

    override fun quickMove(player: PlayerEntity?, invSlot: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots[invSlot]
        if (slot.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()
            if (invSlot == 2) {
                if (!insertItem(originalStack, SLOT_COUNT, slots.size, true)) {
                    return ItemStack.EMPTY
                }
                slot.onQuickTransfer(newStack, originalStack)
            } else if (invSlot < SLOT_COUNT) {
                if (!insertItem(originalStack, SLOT_COUNT, slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (newStack.isOf(EtcherItems.BLANK_DISC)) {
                if (!insertItem(originalStack, 1, 2, true)) {
                    return ItemStack.EMPTY
                }
            } else if(!insertItem(originalStack, 0, SLOT_COUNT, false)) {
                return ItemStack.EMPTY
            }

            if (originalStack.isEmpty) {
                slot.stack = ItemStack.EMPTY
            } else {
                slot.markDirty()
            }

            slot.onTakeItem(player, originalStack)
        }
        return newStack
    }

    private fun updateResult(world: World, pos: BlockPos, inventory: Inventory) {
        if (!world.isClient) {
            if (inventory == input) {
                if (input.getStack(0).isEmpty || input.getStack(1).isEmpty) {
                    output.setStack(0, ItemStack.EMPTY)
                    levelCost.set(0)
                } else {
                    var match = world.recipeManager.getFirstMatch(EtchingRecipe.Type.INSTANCE, input, world)
                    if (match.isPresent) {
                        val recipe = match.get()
                        output.setStack(0, recipe.result)
                        levelCost.set(recipe.levelsRequired)
                    } else {
                        output.setStack(0, ItemStack.EMPTY)
                        levelCost.set(0)
                    }
                }
            }
        }
    }

    override fun onContentChanged(inventory: Inventory) {
        super.onContentChanged(inventory)
        context.run { world, pos ->
            updateResult(world, pos, inventory)
        }
    }

    override fun canUse(player: PlayerEntity): Boolean = context.get({ _: World, pos: BlockPos ->
        player.squaredDistanceTo(
            pos.x.toDouble() + 0.5,
            pos.y.toDouble() + 0.5,
            pos.z.toDouble() + 0.5
        ) <= 64.0
    }, true)

    override fun onClosed(player: PlayerEntity) = context.run { _, _ -> dropInventory(player, input) }

    companion object {
        const val SLOT_COUNT = 3
    }
}