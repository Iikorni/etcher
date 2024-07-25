package io.iikorni.etcher.recipe

import io.iikorni.etcher.Etcher
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.registry.DynamicRegistryManager
import net.minecraft.registry.RegistryWrapper
import net.minecraft.util.Identifier
import net.minecraft.world.World

class EtchingRecipe(val label: Ingredient, val base: Ingredient, val levelsRequired: Int, val result: ItemStack) : Recipe<SimpleInventory> {
    override fun matches(inventory: SimpleInventory, world: World): Boolean = when {
        inventory.size() < 2 -> false
        else -> label.test(inventory.getStack(0)) && base.test(inventory.getStack(1))
    }

    override fun craft(inventory: SimpleInventory?, lookup: RegistryWrapper.WrapperLookup?): ItemStack  = ItemStack.EMPTY

    override fun fits(width: Int, height: Int): Boolean = false

    override fun getResult(registriesLookup: RegistryWrapper.WrapperLookup?) = ItemStack.EMPTY

    override fun getSerializer(): RecipeSerializer<EtchingRecipe> = EtchingRecipeSerializer.INSTANCE

    override fun getType(): RecipeType<*> = Type.INSTANCE

    class Type private constructor() : RecipeType<EtchingRecipe> {
        companion object {
            val INSTANCE = Type()
            val ID = "etching"
        }
    }
}