package io.iikorni.etcher.recipe

import com.google.gson.JsonObject
import io.iikorni.etcher.Etcher
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import java.util.function.Supplier

class EtchingRecipeSerializer private constructor(): RecipeSerializer<EtchingRecipe> {
    override fun write(buf: PacketByteBuf, recipe: EtchingRecipe) {
        recipe.label.write(buf)
        recipe.base.write(buf)
        buf.writeInt(recipe.levelsRequired)
        buf.writeItemStack(recipe.result)
    }

    override fun read(id: Identifier, json: JsonObject): EtchingRecipe {
        val labelElement = when {
            JsonHelper.hasArray(json, "label") -> JsonHelper.getArray(json, "label")
            else -> JsonHelper.getObject(json, "label")
        }
        val label = Ingredient.fromJson(labelElement, false)

        val baseElement = when {
            JsonHelper.hasArray(json, "base") -> JsonHelper.getArray(json, "base")
            else -> JsonHelper.getObject(json, "base")
        }
        val base = Ingredient.fromJson(baseElement, false)

        val levelsRequired = JsonHelper.getInt(json, "levels_required", 0)

        val resultIdentifier = Identifier(JsonHelper.getString(json, "result"))
        val result = ItemStack(
            Registries.ITEM.getOrEmpty(resultIdentifier).orElseThrow { IllegalStateException("Item: $resultIdentifier does not exist") }
        );

        return EtchingRecipe(id, label, base, levelsRequired, result)
    }

    override fun read(id: Identifier, buf: PacketByteBuf): EtchingRecipe {
        val label = Ingredient.fromPacket(buf)
        val base = Ingredient.fromPacket(buf)
        val levelsRequired = buf.readInt()
        val result = buf.readItemStack()
        return EtchingRecipe(id, label, base, levelsRequired, result)
    }

    companion object {
        val INSTANCE = EtchingRecipeSerializer()
        val ID = Identifier(Etcher.MOD_ID, "etching")
    }
}