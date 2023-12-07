package io.iikorni.etcher.recipe

import io.iikorni.etcher.init.EtcherItems
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.registry.DynamicRegistryManager
import net.minecraft.registry.tag.ItemTags
import net.minecraft.util.Identifier
import net.minecraft.world.World

class DiscCloningRecipe(private val _id: Identifier) : Recipe<SimpleInventory> {
    override fun matches(inventory: SimpleInventory, world: World): Boolean = when {
        inventory.size() < 2 -> false
        else -> inventory.getStack(0).isIn(ItemTags.MUSIC_DISCS) && inventory.getStack(1).isOf(EtcherItems.BLANK_DISC)
    }
    override fun craft(inventory: SimpleInventory, registryManager: DynamicRegistryManager): ItemStack {
        val inputDisc = inventory.getStack(0)
        val blankDisc = inventory.getStack(1)

        return when {
            !inputDisc.isIn(ItemTags.MUSIC_DISCS) -> ItemStack.EMPTY
            !blankDisc.isOf(EtcherItems.BLANK_DISC) -> ItemStack.EMPTY
            else -> inputDisc.copy()
        }
    }

    override fun fits(width: Int, height: Int): Boolean = true

    override fun getOutput(registryManager: DynamicRegistryManager?): ItemStack = ItemStack.EMPTY

    override fun getId(): Identifier = _id

    override fun getSerializer(): RecipeSerializer<*> = DiscCloningRecipeSerializer.INSTANCE

    override fun getType(): RecipeType<*> = Type.INSTANCE

    class Type private constructor() : RecipeType<DiscCloningRecipe> {
        companion object {
            val INSTANCE = Type()
            val ID = "etching_disccloning"
        }
    }
}