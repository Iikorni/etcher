package io.iikorni.etcher.recipe

import com.google.gson.JsonObject
import io.iikorni.etcher.Etcher
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier

class DiscCloningRecipeSerializer private constructor(): RecipeSerializer<DiscCloningRecipe> {
    override fun read(id: Identifier, json: JsonObject): DiscCloningRecipe = DiscCloningRecipe(id)

    override fun read(id: Identifier, buf: PacketByteBuf): DiscCloningRecipe = DiscCloningRecipe(id)

    override fun write(buf: PacketByteBuf, recipe: DiscCloningRecipe) {

    }

    companion object {
        val INSTANCE = DiscCloningRecipeSerializer()
        val ID = Identifier(Etcher.MOD_ID, "etching_disccloning")
    }
}