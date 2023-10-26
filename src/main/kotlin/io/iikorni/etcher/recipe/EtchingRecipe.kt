package io.iikorni.etcher.recipe

import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.registry.DynamicRegistryManager
import net.minecraft.world.World

class EtchingRecipe(val label: Ingredient, val base: Ingredient, val levelsRequired: Int, val result: ItemStack) : Recipe<SimpleInventory> {
    override fun matches(inventory: SimpleInventory, world: World): Boolean = when {
        inventory.size() < 2 -> false
        else -> label.test(inventory.getStack(0)) && base.test(inventory.getStack(1))
    }

    override fun craft(inventory: SimpleInventory?, registryManager: DynamicRegistryManager?): ItemStack =
        ItemStack.EMPTY

    override fun fits(width: Int, height: Int): Boolean = false

    override fun getResult(registryManager: DynamicRegistryManager?): ItemStack = result

    override fun getSerializer(): RecipeSerializer<EtchingRecipe> = EtchingRecipeSerializer.INSTANCE

    override fun getType(): RecipeType<*> = Type.INSTANCE

    class Type private constructor() : RecipeType<EtchingRecipe> {
        companion object {
            val INSTANCE = Type()
            val ID = "etching"
        }
    }
}