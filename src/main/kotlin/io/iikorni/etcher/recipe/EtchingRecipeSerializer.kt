package io.iikorni.etcher.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.iikorni.etcher.Etcher
import net.minecraft.item.ItemStack
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier

class EtchingRecipeSerializer private constructor(): RecipeSerializer<EtchingRecipe> {
    private val codec: MapCodec<EtchingRecipe> = RecordCodecBuilder.mapCodec { instance ->
        val product = instance.group(
            Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("label").forGetter { recipe -> recipe.label },
            Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("base").forGetter  { recipe -> recipe.base },
            Codec.INT.fieldOf("levels_required").forGetter { recipe -> recipe.levelsRequired },
            Registries.ITEM.codec.xmap(::ItemStack, ItemStack::getItem).fieldOf("result").forGetter { recipe -> recipe.result}
        )
        product.apply(instance, ::EtchingRecipe)
    }

    private val packet_codec = PacketCodec.ofStatic(::write, ::read)

    private fun write(buf: RegistryByteBuf, recipe: EtchingRecipe) {
        Ingredient.PACKET_CODEC.encode(buf, recipe.label)
        Ingredient.PACKET_CODEC.encode(buf, recipe.base)
        buf.writeInt(recipe.levelsRequired)
        ItemStack.PACKET_CODEC.encode(buf, recipe.result)
    }

    private fun read(buf: RegistryByteBuf): EtchingRecipe {
        val label = Ingredient.PACKET_CODEC.decode(buf)
        val base = Ingredient.PACKET_CODEC.decode(buf)
        val levelsRequired = buf.readInt()
        val result = ItemStack.PACKET_CODEC.decode(buf)
        return EtchingRecipe(label, base, levelsRequired, result)
    }

    companion object {
        val INSTANCE = EtchingRecipeSerializer()
        val ID = Identifier(Etcher.MOD_ID, "etching")
    }

    override fun codec(): MapCodec<EtchingRecipe> = codec

    override fun packetCodec(): PacketCodec<RegistryByteBuf, EtchingRecipe> = packet_codec
}