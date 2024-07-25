package io.iikorni.etcher.recipe

import com.google.gson.JsonObject
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.iikorni.etcher.Etcher
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier

class DiscCloningRecipeSerializer private constructor(): RecipeSerializer<DiscCloningRecipe> {
    private val codec: MapCodec<DiscCloningRecipe> = MapCodec.unit(::DiscCloningRecipe)

    companion object {
        val INSTANCE = DiscCloningRecipeSerializer()
        val ID = Identifier(Etcher.MOD_ID, "etching_disccloning")
    }

    override fun codec(): MapCodec<DiscCloningRecipe> = codec

    override fun packetCodec(): PacketCodec<RegistryByteBuf, DiscCloningRecipe> = PCodec()

    class PCodec() : PacketCodec<RegistryByteBuf, DiscCloningRecipe> {
        override fun decode(buf: RegistryByteBuf?): DiscCloningRecipe = DiscCloningRecipe()

        override fun encode(buf: RegistryByteBuf?, value: DiscCloningRecipe?) {

        }

    }
}